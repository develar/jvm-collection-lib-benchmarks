package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class KotlinObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KotlinxObjectToObjectBenchmark()
    addOperation("kotlin_get", operations, benchmark.get(setup(KotlinxObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("kotlin_put", operations, benchmark.put(state, blackhole))
    addOperation("kotlin_remove", operations, benchmark.remove(state, blackhole))
  }
}