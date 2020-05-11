package org.jetbrains.benchmark.collection

import java.nio.file.Files
import java.nio.file.Paths

private const val packageDir = "org/jetbrains/benchmark/collection"

private val libraries = listOf(
  Library(
    name = "fastutil",
    objectToObjectClassName = "it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap",
    referenceToObjectClassName = "it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap",
    intToIntClassName = "it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap",
    objectToIntClassName = "it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap",
    intToObjectClassName = "it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap",
  ),
  Library(
    name = "trove-jb",
    objectToObjectClassName = "gnu.trove.THashMap",
    referenceToObjectClassName = "gnu.trove.THashMap",
    intToIntClassName = "gnu.trove.TIntIntHashMap",
    intToObjectClassName = "gnu.trove.TIntObjectHashMap",
    objectToIntClassName = "gnu.trove.TObjectIntHashMap",
    factory = "TroveJbFactory"
  ),
  Library(
    name = "trove",
    objectToObjectClassName = "gnu.trove.map.hash.THashMap",
    referenceToObjectClassName = "gnu.trove.map.hash.TCustomHashMap",
    intToIntClassName = "gnu.trove.map.hash.TIntIntHashMap",
    intToObjectClassName = "gnu.trove.map.hash.TIntObjectHashMap",
    objectToIntClassName = "gnu.trove.map.hash.TObjectIntHashMap",
    factory = "TroveFactory",
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
    name = "koloboke",
    objectToObjectClassName = "com.koloboke.collect.map.hash.HashObjObjMap",
    referenceToObjectClassName = "com.koloboke.collect.map.hash.HashObjObjMap",
    intToIntClassName = "com.koloboke.collect.map.hash.HashIntIntMap",
    intToObjectClassName = "com.koloboke.collect.map.hash.HashIntObjMap",
    objectToIntClassName = "com.koloboke.collect.map.hash.HashObjIntMap",
    factory = "KolobokeFactory"
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
)

fun main() {
  val inDir = Paths.get("benchmark/src/$packageDir")

  val outDir = Paths.get("benchmark/generated/$packageDir")
  val existingFiles = Files.newDirectoryStream(outDir).use { it.toHashSet() }

  for (input in listOf(Input("ObjectToObjectBenchmark"), Input("IntToIntBenchmark"), Input("IntToObjectBenchmark"))) {
    val inClassName = input.name
    val inPath = inDir.resolve("$inClassName.java")
    Files.createDirectories(outDir)
    for (library in libraries) {
      val className = "${toCamelCase(library.name)}$inClassName"
      var code = Files.readAllBytes(inPath).toString(Charsets.UTF_8)
      when {
        input.name.startsWith("Object") -> {
          code = replaceNewMap(code, library)
        }
        library.name == "koloboke" -> {
          if (input.name == "IntToObjectBenchmark") {
            code = code
              .replace("HashMap<Integer, ArbitraryPojo> map = new HashMap<>(", "${library.intToObjectClassName}<ArbitraryPojo> map = com.koloboke.collect.map.hash.HashIntObjMaps.newMutableMap(")
              .replace("HashMap<ArbitraryPojo, Integer> map = new HashMap<>(", "${library.objectToIntClassName}<ArbitraryPojo> map = com.koloboke.collect.map.hash.HashObjIntMaps.newMutableMap(")
          }
          else {
            code = code.replace("new HashMap<>(", "com.koloboke.collect.map.hash.HashIntIntMaps.newMutableMap(")
          }
        }
        else -> {
          code = code
            .replace("HashMap<Integer, Integer> map = new HashMap<>(", "${library.intToIntClassName} map = new ${library.intToIntClassName}(")
            .replace("HashMap<Integer, ArbitraryPojo> map = new HashMap<>(", "${library.intToObjectClassName}<ArbitraryPojo> map = new ${library.intToObjectClassName}(")
            .replace("HashMap<ArbitraryPojo, Integer> map = new HashMap<>(", "${library.objectToIntClassName}<ArbitraryPojo> map = new ${library.objectToIntClassName}(")
        }
      }

      code = code
        .replace("import java.util.HashMap;\n", "")
        .replace("import java.util.IdentityHashMap;\n", "")
        .replace("import java.util.Objects;\n", "")
        .replace("class $inClassName", "class $className")
        // space before is important - avoid replacing THashMap
        .replace(" HashMap<ArbitraryPojo, ArbitraryPojo> ", " ${library.objectToObjectClassName}<ArbitraryPojo, ArbitraryPojo> ")
        .replace(" IdentityHashMap<ArbitraryPojo, ArbitraryPojo> ", " ${library.referenceToObjectClassName}<ArbitraryPojo, ArbitraryPojo> ")
        .replace(" HashMap<Integer, Integer> ", " ${library.intToIntClassName} ")
        .replace(" HashMap<Integer, ArbitraryPojo> ", " ${library.intToObjectClassName} ")
        .replace(" HashMap<ArbitraryPojo, Integer> ", " ${library.objectToIntClassName} ")

      if (input.name.startsWith("Int")) {
        if (library.name == "fastutil") {
          if (input.name == "IntToObjectBenchmark") {
            code = code.replace("result ^= Objects.requireNonNullElse(map.get(key), -1)", "result ^= map.getInt(key)")
          }
          code = code.replace("map.remove(keys2[remove++]); // removeInt", "map.removeInt(keys2[remove++]);")
        }
        else if (library.name == "koloboke") {
          if (input.name == "IntToObjectBenchmark") {
            code = code.replace("result ^= Objects.requireNonNullElse(map.get(key), -1)", "result ^= map.getInt(key)")
          }
          code = code.replace("map.remove(keys2[remove++]); // removeInt", "map.removeAsInt(keys2[remove++]);")
        }
        code = code.replace("result ^= Objects.requireNonNullElse(map.get(key), -1)", "result ^= map.get(key)")
      }

      val outFile = outDir.resolve("$className.java")
      existingFiles.remove(outFile)
      Files.write(outFile, code.toByteArray(Charsets.UTF_8))
    }
  }

  existingFiles.forEach {
    println("remove outdated file $it")
    Files.delete(it)
  }
}

private data class Input(val name: String)

private fun replaceNewMap(code: String, library: Library): String {
  val factory = library.factory?.let { "org.jetbrains.benchmark.collection.factory.$it" }
  @Suppress("NAME_SHADOWING")
  var code = code
  if (library.name == "koloboke") {
    code = code.replace("new HashMap<>()", "$factory.createObjectToObject()")
  }
  else {
    code = code.replace("new HashMap<>(", "new ${library.objectToObjectClassName}<>(")
  }

  if (factory == null) {
    return code
      .replace("new IdentityHashMap<>(", "new ${library.referenceToObjectClassName}<>(")
  }
  else {
    return code
      .replace("new HashMap<>(keys.length)", "$factory.createObjectToObject(keys.length)")
      .replace("new IdentityHashMap<>(keys.length)", "$factory.createReferenceToObject(keys.length)")
      .replace("new HashMap<>()", "$factory.createObjectToObject()")
      .replace("new IdentityHashMap<>()", "$factory.createReferenceToObject()")
  }
}

private fun toCamelCase(s: String): String? {
  val builder = StringBuilder()
  for (part in s.splitToSequence("-")) {
    builder.append(part.substring(0, 1).toUpperCase() + part.substring(1))
  }
  return builder.toString()
}

private data class Library(val name: String,
                           val objectToObjectClassName: String,
                           val referenceToObjectClassName: String,
                           val intToIntClassName: String,
                           val intToObjectClassName: String,
                           val objectToIntClassName: String,
                           val factory: String? = null)