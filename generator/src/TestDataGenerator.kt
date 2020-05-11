package org.jetbrains.benchmark.collection

import com.amazon.ion.system.IonTextWriterBuilder
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val testDataDir = Paths.get("testData")
private val textWriterBuilder = IonTextWriterBuilder.minimal().withPrettyPrinting()
private val mapper = IonObjectMapper()

fun main() {
  Files.createDirectories(testDataDir)
  for (size in listOf(100, 1_000, 10_000, 100_000, 1_000_000)) {
    createObject(size)
    createInt(size)
  }
}

private fun createObject(size: Int) {
  val outDir = testDataDir.resolve("${Util.formatSize(size)}.ion")
  val out = Files.newOutputStream(outDir)
  val keys = Array(size) { ArbitraryPojo(generateRandomString(random.nextInt(20)), random.nextInt()) }
  val writer = textWriterBuilder.build(out)
  writer.use {
    mapper.writeValue(writer, keys)
  }

  println("create ${outDir.toAbsolutePath()}")
}

private fun createInt(size: Int) {
  val outDir = testDataDir.resolve("int-${Util.formatSize(size)}.ion")
  val out = Files.newOutputStream(outDir)
  val keys = Array(size) { random.nextInt() }
  val writer = textWriterBuilder.build(out)
  writer.use {
    mapper.writeValue(writer, keys)
  }

  println("create ${outDir.toAbsolutePath()}")
}

// https://www.ssec.wisc.edu/~tomw/java/unicode.html
private val random = Random()

//private fun randObject(): Any? {
//  when (org.jetbrains.benchmark.collection.getRandom.nextInt(8)) {
//    0 -> return Int.MIN_VALUE + org.jetbrains.benchmark.collection.getRandom.nextInt(Int.MAX_VALUE)
//    1 -> return org.jetbrains.benchmark.collection.getRandom.nextLong()
//    2 -> return org.jetbrains.benchmark.collection.getRandom.nextBoolean()
//    3 -> return org.jetbrains.benchmark.collection.getRandom.nextFloat()
//    4 -> return org.jetbrains.benchmark.collection.getRandom.nextDouble()
//    5 -> {
//      val bytes = ByteArray(org.jetbrains.benchmark.collection.getRandom.nextInt(arraySize))
//      org.jetbrains.benchmark.collection.getRandom.nextBytes(bytes)
//      return bytes
//    }
//    6 -> {
//      //val array = ByteArray(org.jetbrains.benchmark.collection.getRandom.nextInt(arraySize / 4))
//      //org.jetbrains.benchmark.collection.getRandom.nextBytes(array)
//      return org.jetbrains.benchmark.collection.generateRandomString()
//    }
//    7 -> return ArbitraryPojo(randObject(), randObject())
//    else -> return null
//  }
//}

@Suppress("SpellCheckingInspection")
private const val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

private fun generateRandomString(n: Int): String? {
  val sb = StringBuilder(n)
  for (i in 0 until n) {
    sb.append(chars.get(random.nextInt(chars.length)))
  }
  return sb.toString()
}