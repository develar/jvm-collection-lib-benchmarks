import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@Serializable
internal data class JmhResult(
  @JvmField val benchmark: String,
  @JvmField val params: Params,
  @JvmField val primaryMetric: Metric
) {
  @Serializable
  data class Metric(@JvmField val scorePercentiles: Percentiles)

  @Serializable
  data class Params(@JvmField val mapSize: String, @JvmField val loadFactor: String)

  @Serializable
  data class Percentiles(@JvmField @SerialName("50.0") val p50: Double)
}

private abstract class CategorizeRule(@JvmField val regex: Regex) {
  abstract fun getGroupAndCategory(matchResult: MatchResult, benchmark: JmhResult): GroupAndCategory
}

private val categorizeRules = listOf(
  object : CategorizeRule(regex = Regex("^([a-zA-Z]+)MapString")) {
    override fun getGroupAndCategory(matchResult: MatchResult, benchmark: JmhResult): GroupAndCategory {
      var category = matchResult.groups.get(1)!!.value.camelToSnakeCase()
      if (category == "hash") {
        category = "java"
      }
      category = addParamsToCategory(benchmark, category)
      return GroupAndCategory(category = category, group = "String")
    }
  },
  object : CategorizeRule(regex = Regex("^(Androidx|Kotlinx|Java|Ec|Fastutil|Hppc|)([a-zA-Z]+)")) {
    override fun getGroupAndCategory(matchResult: MatchResult, benchmark: JmhResult): GroupAndCategory {
      var category = matchResult.groups[1]!!.value.camelToSnakeCase()
      if (category.isEmpty()) {
        category = "java"
      }
      category = addParamsToCategory(benchmark, category)

      var group = matchResult.groups[2]!!.value
      if (group == "OrderedObjectToObject") {
        group = "LinkedMap"
      }
      return GroupAndCategory(category = category, group = group)
    }
  },
)

private fun String.camelToSnakeCase(): String {
  val pattern = Regex("(?<=.)[A-Z]")
  return this.replace(pattern, "-$0").lowercase()
}

private fun addParamsToCategory(benchmark: JmhResult, category: String): String {
  val loadFactor = benchmark.params.loadFactor
  if (loadFactor != "0.75") {
    return "$category-$loadFactor"
  }
  return category
}

internal data class GroupAndCategory(@JvmField val group: String, @JvmField val category: String)

private fun getCategory(className: String, benchmark: JmhResult): GroupAndCategory {
  for (categorizeRule in categorizeRules) {
    val result = categorizeRule.regex.matchEntire(className)
    if (result != null) {
      return categorizeRule.getGroupAndCategory(result, benchmark)
    }
  }

  throw IllegalStateException("Cannot categorize $className")
}

fun main() {
  val input = Path.of("jmh-result.json")
  Files.copy(input, Path.of("result/mac/jmh-result.json"), StandardCopyOption.REPLACE_EXISTING)

  val benchmarks = json.decodeFromString<List<JmhResult>>(Files.readString(input))
  val sizes = linkedSetOf<String>()
  val groups: MutableMap<String, MutableMap<String, MutableMap<String, MutableMap<String, Double>>>> = LinkedHashMap()
  val debug = LinkedHashSet<String>()
  for (benchmark in benchmarks) {
    val (groupAndCategory, operation) = getItemDescriptor(benchmark, debug)
    sizes.add(benchmark.params.mapSize)

    val existing = groups
      .computeIfAbsent(groupAndCategory.group) { LinkedHashMap() }
      .computeIfAbsent(benchmark.params.mapSize) { LinkedHashMap() }
      .computeIfAbsent(operation) { LinkedHashMap() }
      .put(groupAndCategory.category, benchmark.primaryMetric.scorePercentiles.p50)
    check(existing == null) {
      "${groupAndCategory.category} already added"
    }
  }

  println(debug.joinToString(separator = "\n"))

  writeData("data", sizes, groups)
}

internal fun getItemDescriptor(benchmark: JmhResult, debug: MutableSet<String>? = null): Pair<GroupAndCategory, String> {
  val classAndMethod = benchmark.benchmark.removePrefix("org.jetbrains.benchmark.collection.")
  val lastDot = classAndMethod.lastIndexOf('.')
  val className = classAndMethod.substring(0, lastDot).removeSuffix("Benchmark")
  var groupAndCategory = getCategory(className, benchmark)
  debug?.add("$className maps to ${groupAndCategory.group}")
  var operation = classAndMethod.substring(lastDot + 1)

  if ((groupAndCategory.group == "IntToObject" || groupAndCategory.group == "AndroidxIntToObject") && operation.startsWith("object")) {
    groupAndCategory = GroupAndCategory(group = "ObjectToInt", category = groupAndCategory.category)
    operation = operation.removePrefix("object").lowercase()
  }

  if (!(operation == "get" || operation == "remove" || operation == "put")) {
    throw IllegalStateException("Unsupported operation: $operation")
  }
  return Pair(groupAndCategory, operation)
}