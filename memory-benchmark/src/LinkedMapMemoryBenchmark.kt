package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class JavaLinkedMapMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = JavaLinkedMapBenchmark()
    addOperation("java_get", operations, benchmark.get(setup(JavaLinkedMapBenchmark.BenchmarkGetState(), size), blackhole))

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class FastutilLinkedMapMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilLinkedMapBenchmark()
    addOperation("fastutil_get", operations, benchmark.get(setup(FastutilLinkedMapBenchmark.BenchmarkGetState(), size), blackhole))

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }
}