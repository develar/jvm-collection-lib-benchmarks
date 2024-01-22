import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import kotlinx.serialization.json.internal.writeJson
import org.jetbrains.benchmark.collection.*
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Path

fun main() {
  @Suppress("SpellCheckingInspection")
  val blackhole = Blackhole("Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.")

  val sizes = sequenceOf(100, 1_000, 10_00, 100_000, 1_000_000, 10_000_000).map { Util.formatSize(it) }.toList()
  //val sizes = sequenceOf(100, 1_000, 10_00).map { Util.formatSize(it) }.toList()
  val types = listOf("IntToInt", "IntToObject", "ObjectToInt", "ObjectToObject", "ReferenceToObject", "LinkedMap")
  val measurers = measurers + KotlinObjectToObjectMemoryBenchmark() + KotlinLinkedMapMemoryBenchmark()
  val groups: MutableMap<String, MutableMap<String, MutableMap<String, MutableMap<String, Double>>>> = LinkedHashMap()
  for (type in types) {
    val map = LinkedHashMap<String, MutableMap<String, MutableMap<String, Double>>>()
    groups.put(type, map)
    for (size in sizes) {
      val subMap = LinkedHashMap<String, MutableMap<String, Double>>()
      map.put(size, subMap)
      println("measure $type for size $size")
      val operations = Object2LongArrayMap<String>()

      for (measurer in measurers) {
        if (measurer.javaClass.name.contains(type)) {
          measurer.measure(size, operations, blackhole)
        }
      }
      for (operationEntry in operations.object2LongEntrySet().fastIterator()) {
        val pair = operationEntry.key.split('_', limit = 2)
         subMap
          .computeIfAbsent(pair[1]) { LinkedHashMap() }
          .put(pair[0], operationEntry.longValue.toDouble())
      }
    }
  }

  writeData("memory-data", sizes, groups)
  //writeJson("memoryChartData", Path.of("site", "memory-data.js")) { writer ->
  //  writer.writeStartObject()
  //  for (entry in typeToData.object2ObjectEntrySet().fastIterator()) {
  //    writer.writeArrayFieldStart(entry.key)
  //
  //    for (sizeEntry in entry.value.object2ObjectEntrySet().fastIterator()) {
  //      writer.writeStartObject()
  //      writer.writeStringField("size", sizeEntry.key)
  //      for (operationEntry in sizeEntry.value.object2LongEntrySet().fastIterator()) {
  //        writer.writeNumberField(operationEntry.key, operationEntry.longValue)
  //      }
  //      writer.writeEndObject()
  //    }
  //
  //    writer.writeEndArray()
  //  }
  //
  //  writer.writeEndObject()
  //}
}