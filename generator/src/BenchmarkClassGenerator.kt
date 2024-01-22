package org.jetbrains.benchmark.collection

import java.nio.file.Files
import java.nio.file.Path

private const val packageDir = "org/jetbrains/benchmark/collection"

internal val libraries = listOf(
  Library(
    name = "fastutil",
    objectToObjectClassName = "it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap",
    referenceToObjectClassName = "it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap",
    intToIntClassName = "it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap",
    objectToIntClassName = "it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap",
    intToObjectClassName = "it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap",
  ),
  Library(
    name = "hppc",
    objectToObjectClassName = "com.carrotsearch.hppc.ObjectObjectHashMap",
    referenceToObjectClassName = "com.carrotsearch.hppc.ObjectObjectIdentityHashMap",
    intToIntClassName = "com.carrotsearch.hppc.IntIntHashMap",
    intToObjectClassName = "com.carrotsearch.hppc.IntObjectHashMap",
    objectToIntClassName = "com.carrotsearch.hppc.ObjectIntHashMap",
  ),
  Library(
    name = "ec",
    objectToObjectClassName = "org.eclipse.collections.impl.map.mutable.UnifiedMap",
    referenceToObjectClassName = "org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy",
    intToIntClassName = "org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap",
    intToObjectClassName = "org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap",
    objectToIntClassName = "org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap",
    factory = "EcFactory"
  ),
  Library(
    name = "androidx",
    objectToObjectClassName = "androidx.collection.MutableScatterMap",
    referenceToObjectClassName = "androidx.collection.MutableScatterMap",
    intToIntClassName = "androidx.collection.MutableIntIntMap",
    intToObjectClassName = "androidx.collection.MutableIntObjectMap",
    objectToIntClassName = "androidx.collection.MutableObjectIntMap",
    factory = "AndroidxFactory"
  ),
)

private val memoryBenchmarkClassNames =
  listOf("IntToIntMemoryBenchmark", "IntToObjectMemoryBenchmark", "ObjectToIntMemoryBenchmark", "ObjectToObjectMemoryBenchmark", "ReferenceToObjectMemoryBenchmark")

fun main() {
  val inDir = Path.of("benchmark/src/$packageDir")

  val outDir = Path.of("benchmark/generated/$packageDir")
  val existingFiles = Files.newDirectoryStream(outDir).use { it.toHashSet() }

  generateMemoryBenchmarks()

  Files.createDirectories(outDir)

  generateBenchmarks(inDir, outDir, existingFiles)
  generateLinkedMapBenchmarks(inDir, outDir, existingFiles)

  for (file in existingFiles) {
    println("remove outdated file $file")
    Files.delete(file)
  }
}

private fun generateMemoryBenchmarks() {
  val memoryBenchmarkCode = Files.readString(Path.of("memory-benchmark/src/MemoryBenchmark.kt"))
  val memoryBenchmarkOutDir = Path.of("memory-benchmark/generated")
  Files.createDirectories(memoryBenchmarkOutDir)
  var memoryMeasurerListCode = "package org.jetbrains.benchmark.collection\n\nval measurers: List<Measurer> = listOf("
  for (name in memoryBenchmarkClassNames) {
    memoryMeasurerListCode += "\n  $name(),"
  }

  for (library in libraries) {
    var code = memoryBenchmarkCode
    val classPrefix = library.classPrefix
    code = code
      .replace("class ", "class $classPrefix")
      .replace("java_", "${library.name}_")
    for (name in listOf("IntToIntBenchmark", "IntToObjectBenchmark", "ObjectToObjectBenchmark", "ReferenceToObjectBenchmark", "LinkedMapMemoryBenchmark")) {
      code = code
        .replace(" $name(", " ${classPrefix}$name(")
        .replace(": $name", ": ${classPrefix}$name")
        .replace("= $name.", "= ${classPrefix}$name.")
        .replace("($name.", "(${classPrefix}$name.")
    }

    for (name in memoryBenchmarkClassNames) {
      memoryMeasurerListCode += "\n  $classPrefix$name(),"
    }

    Files.writeString(memoryBenchmarkOutDir.resolve("${classPrefix}MemoryBenchmark.kt"), code)
  }

  memoryMeasurerListCode += "\n  JavaLinkedMapMemoryBenchmark(),"
  memoryMeasurerListCode += "\n  FastutilLinkedMapMemoryBenchmark(),"
  memoryMeasurerListCode += "\n)"
  Files.writeString(memoryBenchmarkOutDir.resolve("list.kt"), memoryMeasurerListCode)
}

private fun generateBenchmarks(inDir: Path, outDir: Path, existingFiles: MutableSet<Path>) {
  for (input in listOf(
    Input("ObjectToObjectBenchmark"),
    Input("ReferenceToObjectBenchmark"),
    Input("IntToIntBenchmark"),
    Input("IntToObjectBenchmark"),
    Input("HashMapStringBenchmark"),
  )) {
    val inClassName = input.name
    val inPath = inDir.resolve("$inClassName.java")

    val inputCode = Files.readString(inPath)

    for (library in libraries) {
      if (input.name == "HashMapStringBenchmark" && library.name != "fastutil") {
        continue
      }

      val className = "${library.classPrefix}${inClassName.removePrefix("Hash")}"
      var code = inputCode
      code = code
        .replace("import java.util.HashMap;\n", "")
        .replace("import java.util.IdentityHashMap;\n", "")
        .replace("import java.util.Objects;\n", "")
        .replace("class $inClassName", "class $className")

      when {
        input.name == "ObjectToObjectBenchmark" -> {
          code = replaceNewMap(code = code, library = library, useFactory = false)
            .replace("<ObjectToObjectBenchmark.BenchmarkGetState>", "<$className.BenchmarkGetState>")
        }
        input.name == "HashMapStringBenchmark" -> {
          code = replaceNewMap(code = code, library = library, useFactory = false)
            .replace("<HashMapStringBenchmark.StringBenchmarkState>", "<$className.StringBenchmarkState>")
        }
        input.name == "ReferenceToObjectBenchmark" -> {
          code = replaceNewMap(code, library, useFactory = true)
            .replace("<ReferenceToObjectBenchmark.BenchmarkGetState>", "<$className.BenchmarkGetState>")
        }
        library.name == "androidx" -> {
          if (input.name == "IntToObjectBenchmark") {
            code = code
              .replace("HashMap<ArbitraryPojo, Integer> map = new HashMap<>(0, state.loadFactor)", "HashMap<ArbitraryPojo, Integer> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createObjectToInt(state.loadFactor)")
              .replace("HashMap<ArbitraryPojo, Integer> map = new HashMap<>(", "HashMap<ArbitraryPojo, Integer> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createObjectToInt(")
              .replace("HashMap<Integer, ArbitraryPojo> map = new HashMap<>(0, state.loadFactor)", "HashMap<Integer, ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToObject(state.loadFactor)")
              .replace("HashMap<Integer, ArbitraryPojo> map = new HashMap<>(", "HashMap<Integer, ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToObject(")
          }
          else {
            code = code
              .replace("new HashMap<>(0, state.loadFactor)", "org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToInt(state.loadFactor)")
              .replace("new HashMap<>(", "org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToInt(")
          }
        }
      }

      if (library.name == "androidx") {
        code = code.replace("map.size()", "map._size")
        if (input.name == "IntToIntBenchmark") {
          code = code.replace("Objects.requireNonNullElse(map.get(key), -1)", "map.getOrDefault(key, -1)")
          code = code.replace("map.get(key)", "map.getOrDefault(key, 0)")
        } else {
          code = code.replace("Objects.requireNonNullElse(map.get(key), -1)", "map.getOrDefault(key, -1)")
          code = code.replace("map.get(key)", "map.getOrDefault(key, null)")
        }
      }

      if (input.name.startsWith("Int")) {
        code = replaceInt(code, input, library)
      }
      else {
        // space before is important - avoid replacing THashMap
        code = code
          .replace(" HashMap<ArbitraryPojo, ArbitraryPojo> ", " ${library.objectToObjectClassName}<ArbitraryPojo, ArbitraryPojo> ")
          .replace(" HashMap<String, String> ", " ${library.objectToObjectClassName}<String, String> ")
          .replace(" IdentityHashMap<ArbitraryPojo, ArbitraryPojo> ", " ${library.referenceToObjectClassName}<ArbitraryPojo, ArbitraryPojo> ")
      }

      val outFile = outDir.resolve("$className.java")
      existingFiles.remove(outFile)
      Files.write(outFile, code.toByteArray(Charsets.UTF_8))
    }
  }
}

private fun generateLinkedMapBenchmarks(inDir: Path, outDir: Path, existingFiles: MutableSet<Path>) {
  val inClassName = "ObjectToObjectBenchmark"
  val inPath = inDir.resolve("$inClassName.java")

  val inputCode = Files.readAllBytes(inPath).toString(Charsets.UTF_8)

  data class Item(val libraryName: String, val className: String)

  for (item in listOf(Item("java", "java.util.LinkedHashMap"), Item("fastutil", "it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap"))) {
    val className = "${toCamelCase(item.libraryName)}LinkedMapBenchmark"
    var code = inputCode
    val shortClassName = item.className.substring(item.className.lastIndexOf('.') + 1)
    code = code
      .replace("import java.util.HashMap;", "import ${item.className};")
      .replace("class $inClassName", "class $className")
      .replace("HashMap<", "${shortClassName}<")
      .replace("<ObjectToObjectBenchmark.BenchmarkGetState>", "<$className.BenchmarkGetState>")

    val outFile = outDir.resolve("$className.java")
    existingFiles.remove(outFile)
    Files.write(outFile, code.toByteArray(Charsets.UTF_8))
  }
}

private fun replaceInt(code: String, input: Input, library: Library): String {
  @Suppress("NAME_SHADOWING")
  var code = code

  code = code
    .replace("HashMap<Integer, Integer> map = new HashMap<>(", "${library.intToIntClassName} map = new ${library.intToIntClassName}(")
    .replace("HashMap<Integer, ArbitraryPojo> map = new HashMap<>(", "${library.intToObjectClassName}<ArbitraryPojo> map = new ${library.intToObjectClassName}(")
    .replace("HashMap<ArbitraryPojo, Integer> map = new HashMap<>(", "${library.objectToIntClassName}<ArbitraryPojo> map = new ${library.objectToIntClassName}(")

  if (library.name == "ec") {
    code = code
      .replace(", loadFactor);", ");")
      .replace("0, state.loadFactor);", ");")
  }

  code = code
    .replace(" HashMap<Integer, Integer> ", " ${library.intToIntClassName} ")
    .replace(" HashMap<Integer, ArbitraryPojo> ", " ${library.intToObjectClassName}<ArbitraryPojo> ")
    .replace(" HashMap<ArbitraryPojo, Integer> ", " ${library.objectToIntClassName}<ArbitraryPojo> ")

  if (library.name == "fastutil") {
    if (input.name == "IntToObjectBenchmark") {
      code = code.replace("result ^= Objects.requireNonNullElse(map.get(key), -1)", "result ^= map.getInt(key)")
    }
    code = code.replace("map.remove(keys2[remove++]); // removeInt", "map.removeInt(keys2[remove++]);")
  }
  code = code.replace("result ^= Objects.requireNonNullElse(map.get(key), -1)", "result ^= map.get(key)")
  return code
}

private data class Input(@JvmField val name: String)

private fun replaceNewMap(code: String, library: Library, useFactory: Boolean): String {
  val factory = if (useFactory) library.factory?.let { "org.jetbrains.benchmark.collection.factory.$it" } else null
  if (factory == null) {
    return code
      .replace("new IdentityHashMap<>(", "new ${library.referenceToObjectClassName}<>(")
      .replace("new HashMap<>(", "new ${library.objectToObjectClassName}<>(")
  }
  else {
    return code
      .replace("new HashMap<>(keys.length, loadFactor)", "$factory.createObjectToObject(keys.length, loadFactor)")
      .replace("new IdentityHashMap<>(keys.length)", "$factory.createReferenceToObject(keys.length, loadFactor)")
      .replace("new HashMap<>(0, state.loadFactor)", "$factory.createObjectToObject(state.loadFactor)")
      .replace("new IdentityHashMap<>()", "$factory.createReferenceToObject(state.loadFactor)")
  }
}

private fun toCamelCase(s: String): String {
  val builder = StringBuilder()
  for (part in s.splitToSequence("-")) {
    builder.append(part.substring(0, 1).uppercase() + part.substring(1))
  }
  return builder.toString()
}

data class Library(val name: String,
                   val objectToObjectClassName: String,
                   val referenceToObjectClassName: String,
                   val intToIntClassName: String,
                   val intToObjectClassName: String,
                   val objectToIntClassName: String,
                   val factory: String? = null) {
  val classPrefix: String
    get() = if (name == "java") "" else toCamelCase(name)
}