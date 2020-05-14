package org.jetbrains.benchmark.collection

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.IntFunction

private class JmhResult @JsonCreator constructor(
  @JsonProperty("benchmark") val benchmark: String,
  @JsonProperty("params") val params: Params,
  @JsonProperty("primaryMetric") val primaryMetric: Metric
) {
  class Metric @JsonCreator constructor(@JsonProperty("scorePercentiles") val scorePercentiles: Percentiles)

  class Params @JsonCreator constructor(@JsonProperty("mapSize") val mapSize: String)

  class Percentiles @JsonCreator constructor(@JsonProperty("50.0") val p50: Float)
}

private val javaLibrary = Library(
  name = "java",
  objectToObjectClassName = "HashMap",
  referenceToObjectClassName = "IdentityHashMap",
  intToIntClassName = "HashMap",
  objectToIntClassName = "HashMap",
  intToObjectClassName = "HashMap",
)

val librariesWithJava = libraries + javaLibrary

fun main() {
  val benchmarks = ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .readValue(Paths.get("jmh-result.json").toFile(), Array<JmhResult>::class.java)
  val result = LinkedHashMap<String, Object2ObjectOpenHashMap<Library, Int2ObjectOpenHashMap<Entry>>>()
  for (benchmark in benchmarks) {
    val classAndMethod = benchmark.benchmark.substring("org.jetbrains.benchmark.collection.".length)
    val lastDot = classAndMethod.lastIndexOf('.')
    var operation = classAndMethod.substring(lastDot + 1)

    var type = classAndMethod.substring(0, lastDot).removeSuffix("Benchmark")
    var library = javaLibrary
    for (l in libraries) {
      if (type.startsWith(l.classPrefix)) {
        type = type.substring(l.classPrefix.length)
        library = l
        break
      }
    }

    if (type == "IntToObject" && operation.startsWith("object")) {
      type = "ObjectToInt"
      operation = operation.removePrefix("object").toLowerCase()
    }
    else if (type == "ObjectToObject" && operation.startsWith("identity")) {
      type = "ReferenceToObject"
      operation = operation.removePrefix("identity").toLowerCase()
    }

    if (!(operation == "get" || operation == "remove" || operation == "put")) {
      throw IllegalStateException("Unsupported operation: $operation")
    }

    val size = Util.parseSize(benchmark.params.mapSize)
    val entryMap = result.computeIfAbsent(type) { Object2ObjectOpenHashMap() }.computeIfAbsent(library) { Int2ObjectOpenHashMap() }
    val entry = entryMap.computeIfAbsent(size, IntFunction { Entry(it) })
    val value = benchmark.primaryMetric.scorePercentiles.p50
    if (entry.operations.put(operation, value) > 0) {
      throw IllegalStateException("Operation value is already set: $operation")
    }
  }

  writeJson("chartData", Paths.get("site", "data.js")) { writer ->
    writeJson(result, writer)
  }
}

inline fun writeJson(variableName: String, outFile: Path, task: (JsonGenerator) -> Unit) {
  Files.createDirectories(outFile.parent)
  Files.newBufferedWriter(outFile).use { out ->
    out.write("\"use strict\"\n")
    out.write("\nconst $variableName = ")

    val jsonGenerator = JsonFactory().createGenerator(out).useDefaultPrettyPrinter()
    jsonGenerator.use {
      task(jsonGenerator)
    }
  }
}

fun writeSizes(writer: JsonGenerator, sizes: Sequence<Int>) {
  writer.writeArrayFieldStart("sizes")
  for (size in sizes) {
    writer.writeString(Util.formatSize(size))
  }
  writer.writeEndArray()
}

private fun writeJson(typeToLibraryResult: Map<String, Map<Library, Int2ObjectOpenHashMap<Entry>>>, writer: JsonGenerator) {
  writer.writeStartObject()

  writeSizes(writer, typeToLibraryResult.values.first().values.first().values.asSequence().map { it.size }.sorted())

  for ((type, libraryToResult) in typeToLibraryResult) {
    writer.writeArrayFieldStart(type)
    for (library in libraryToResult.keys.asSequence().sortedBy { it.name }) {
      writer.writeStartObject()
      writer.writeStringField("name", library.name)

      writer.writeArrayFieldStart("data")
      for (entry in libraryToResult.getValue(library).values.sortedBy { it.size }) {
        writer.writeStartObject()
        writer.writeStringField("size", Util.formatSize(entry.size))
        for (operationAndValue in entry.operations.object2FloatEntrySet().fastIterator()) {
          writer.writeNumberField(operationAndValue.key, operationAndValue.floatValue)
        }
        writer.writeEndObject()
      }
      writer.writeEndArray()

      writer.writeEndObject()
    }
    writer.writeEndArray()
  }
  writer.writeEndObject()
}

//} //private static void write(String type, LinkedHashMap<String, List<String>> result) throws IOException {

  //  for (Map.Entry<String, List<String>> subTypeToLines : result.entrySet()) {
  //    System.out.println("-".repeat(64));
  //    String subType = subTypeToLines.getKey();
  //    String name = type + " " + subType;
  //    System.out.println(name);
  //    List<String> subLines = subTypeToLines.getValue();
  //    System.out.println(String.join("\n", subLines));
  //    Files.write(Paths.get("results", name + ".csv"), subLines, StandardCharsets.UTF_8);
  //  }
  //}

internal class Entry(val size: Int) {
  val operations = Object2FloatArrayMap<String>(3)

  init {
    operations.defaultReturnValue(-1f)
  }
}
