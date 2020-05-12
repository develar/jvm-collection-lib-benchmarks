import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jetbrains.benchmark.collection.*
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Paths

fun main() {
  @Suppress("SpellCheckingInspection")
  val blackhole = Blackhole("Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.")

  val sizes = sequenceOf(100, 1_000, 10_00, 100_000, 1_000_000, 10_000_000).map { Util.formatSize(it) }.toList()
  val types = listOf("IntToInt", "IntToObject", "ObjectToInt", "ObjectToObject", "ReferenceToObject")
  val typeToData = Object2ObjectArrayMap<String, Object2ObjectArrayMap<String, Object2LongArrayMap<String>>>()
  for (type in types) {
    val sizeToOperations = Object2ObjectArrayMap<String, Object2LongArrayMap<String>>()
    typeToData.put(type, sizeToOperations)
    for (size in sizes) {
      println("measure $type for size $size")
      val operations = Object2LongArrayMap<String>()
      sizeToOperations.put(size, operations)

      for (measurer in measurers) {
        if (measurer.javaClass.name.contains(type)) {
          measurer.measure(size, operations, blackhole)
        }
      }
    }
  }

  writeJson("memoryChartData", Paths.get("site", "memory.js")) { writer ->
    writer.writeStartObject()
    writeSizes(writer, sizes.asSequence().map { Util.parseSize(it) })

    writer.writeArrayFieldStart("series")
    for (lib in librariesWithJava.sortedBy { it.name }) {
      writer.writeString(lib.name)
    }
    writer.writeEndArray()

    for (entry in typeToData.object2ObjectEntrySet().fastIterator()) {
      writer.writeArrayFieldStart(entry.key)

      for (sizeEntry in entry.value.object2ObjectEntrySet().fastIterator()) {
        writer.writeStartObject()
        writer.writeStringField("size", sizeEntry.key)
        for (operationEntry in sizeEntry.value.object2LongEntrySet().fastIterator()) {
          writer.writeNumberField(operationEntry.key, operationEntry.longValue)
        }
        writer.writeEndObject()
      }

      writer.writeEndArray()
    }

    writer.writeEndObject()
  }
}