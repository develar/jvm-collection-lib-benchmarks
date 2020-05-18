package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class IntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    setup(state, size)
    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: IntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = IntToIntBenchmark.GetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("java_get", operations, state.map)
  }
}

internal class IntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: IntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = IntToObjectBenchmark.IntToObjectGetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("java_get", operations, state.map)
  }
}

internal class ObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("java_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("java_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: IntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = IntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    setup(state, size)

    benchmark.objectGet(state, blackhole)
    addOperation("java_get", operations, state.map)
  }
}

internal class ObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = ObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: ObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = ObjectToObjectBenchmark.BenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("java_get", operations, state.map)
  }
}

internal class ReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = ReferenceToObjectMapBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: ReferenceToObjectMapBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = ReferenceToObjectMapBenchmark.IdentityBenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("java_get", operations, state.map)
  }
}