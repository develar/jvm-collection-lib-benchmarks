package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class HppcIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    setup(state, size)
    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToIntBenchmark.GetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("hppc_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    setup(state, size)

    benchmark.objectGet(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcObjectToObjectBenchmark.BenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcReferenceToObjectMapBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcReferenceToObjectMapBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcReferenceToObjectMapBenchmark.IdentityBenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}