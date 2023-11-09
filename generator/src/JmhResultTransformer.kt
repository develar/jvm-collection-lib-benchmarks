package org.jetbrains.benchmark.collection

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.BiFunction
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

private val kotlinLibrary = Library(
  name = "kotlin",
  objectToObjectClassName = "",
  referenceToObjectClassName = "",
  intToIntClassName = "",
  objectToIntClassName = "",
  intToObjectClassName = "",
)

private val librariesWithJava = libraries + javaLibrary + kotlinLibrary

fun main() {
  val benchmarks = ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .readValue(Path.of("result/mac/jmh-result.json").toFile(), Array<JmhResult>::class.java)
  val result = LinkedHashMap<String, Int2ObjectOpenHashMap<MutableCollection<Entry>>>()
  for (benchmark in benchmarks) {
    val classAndMethod = benchmark.benchmark.substring("org.jetbrains.benchmark.collection.".length)
    val lastDot = classAndMethod.lastIndexOf('.')
    var operation = classAndMethod.substring(lastDot + 1)

    var type = classAndMethod.substring(0, lastDot).removeSuffix("Benchmark")
    var library = if (classAndMethod.contains("Kotlin")) kotlinLibrary else javaLibrary
    for (l in libraries) {
      if (type.startsWith(l.classPrefix)) {
        type = type.substring(l.classPrefix.length)
        library = l
        break
      }
    }

    if (library == javaLibrary && type.startsWith("Java")) {
      type = type.substring("Java".length)
    }

    if (type == "IntToObject" && operation.startsWith("object")) {
      type = "ObjectToInt"
      operation = operation.removePrefix("object").lowercase()
    }

    if (type == "KotlinxOrderedObjectToObject") {
      type = "LinkedMap"
    }
    else if (type == "KotlinxObjectToObject") {
      type = "ObjectToObject"
    }

    if (!(operation == "get" || operation == "remove" || operation == "put")) {
      throw IllegalStateException("Unsupported operation: $operation")
    }

    val size = Util.parseSize(benchmark.params.mapSize)
    val entry = Entry(operation, benchmark.primaryMetric.scorePercentiles.p50, library)
    val list = result.computeIfAbsent(type) { Int2ObjectOpenHashMap() }.computeIfAbsent(size, IntFunction { ArrayList() })
    if (list.any { it.operation == entry.operation && it.library == entry.library }) {
      throw IllegalStateException("Operation value is already set: $operation")
    }
    list.add(entry)
  }

  writeJson("chartData", Path.of("site", "data.js")) { writer ->
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

fun writeSizesAndSeries(writer: JsonGenerator, sizes: Sequence<Int>) {
  writer.writeArrayFieldStart("sizes")
  for (size in sizes) {
    writer.writeString(Util.formatSize(size))
  }
  writer.writeEndArray()

  writer.writeArrayFieldStart("series")
  for (lib in librariesWithJava.sortedBy { it.name }) {
    writer.writeString(lib.name)
  }
  writer.writeEndArray()
}

private fun writeJson(typeToLibraryResult: LinkedHashMap<String, Int2ObjectOpenHashMap<MutableCollection<Entry>>>, writer: JsonGenerator) {
  writer.writeStartObject()

  writeSizesAndSeries(writer, typeToLibraryResult.values.first().keys.asSequence().map { it }.sorted())

  for ((type, sizeToEntries) in typeToLibraryResult) {
    writer.writeArrayFieldStart(type)
    for (size in sizeToEntries.keys.asSequence().sortedBy { it }) {
      writer.writeStartObject()
      writer.writeStringField("size", Util.formatSize(size))
      for (entry in sizeToEntries.get(size)) {
        writer.writeNumberField("${entry.library.name}_${entry.operation}", entry.value)
      }
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

internal data class Entry(val operation: String, val value: Float, val library: Library)
