import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import org.jetbrains.benchmark.collection.*
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Path

fun main() {
  @Suppress("SpellCheckingInspection")
  val blackhole = Blackhole("Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.")

  val sizes = sequenceOf(100, 1_000, 10_00, 100_000, 1_000_000, 10_000_000).map { Util.formatSize(it) }.toList()
  val types = listOf("IntToInt", "IntToObject", "ObjectToInt", "ObjectToObject", "ReferenceToObject", "LinkedMap")
  val typeToData = Object2ObjectArrayMap<String, Object2ObjectArrayMap<String, Object2LongArrayMap<String>>>()
  val measurers = measurers + KotlinObjectToObjectMemoryBenchmark() + KotlinLinkedMapMemoryBenchmark()
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

  writeJson("memoryChartData", Path.of("site", "memory-data.js")) { writer ->
    writer.writeStartObject()
    writeSizesAndSeries(writer, sizes.asSequence().map { Util.parseSize(it) })

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