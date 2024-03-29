package org.jetbrains.benchmark.collection

val measurers: List<Measurer> = listOf(
  IntToIntMemoryBenchmark(),
  IntToObjectMemoryBenchmark(),
  ObjectToIntMemoryBenchmark(),
  ObjectToObjectMemoryBenchmark(),
  ReferenceToObjectMemoryBenchmark(),
  FastutilIntToIntMemoryBenchmark(),
  FastutilIntToObjectMemoryBenchmark(),
  FastutilObjectToIntMemoryBenchmark(),
  FastutilObjectToObjectMemoryBenchmark(),
  FastutilReferenceToObjectMemoryBenchmark(),
  HppcIntToIntMemoryBenchmark(),
  HppcIntToObjectMemoryBenchmark(),
  HppcObjectToIntMemoryBenchmark(),
  HppcObjectToObjectMemoryBenchmark(),
  HppcReferenceToObjectMemoryBenchmark(),
  EcIntToIntMemoryBenchmark(),
  EcIntToObjectMemoryBenchmark(),
  EcObjectToIntMemoryBenchmark(),
  EcObjectToObjectMemoryBenchmark(),
  EcReferenceToObjectMemoryBenchmark(),
  AndroidxIntToIntMemoryBenchmark(),
  AndroidxIntToObjectMemoryBenchmark(),
  AndroidxObjectToIntMemoryBenchmark(),
  AndroidxObjectToObjectMemoryBenchmark(),
  AndroidxReferenceToObjectMemoryBenchmark(),
  JavaLinkedMapMemoryBenchmark(),
  FastutilLinkedMapMemoryBenchmark(),
)