package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class HppcIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToIntBenchmark.GetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("hppc_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.objectGet(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcObjectToObjectBenchmark.BenchmarkGetState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}

internal class HppcReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("hppc_put", operations, benchmark.identityPut(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.identityRemove(state, blackhole))
  }

  private fun measureGet(benchmark: HppcObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = HppcObjectToObjectBenchmark.IdentityBenchmarkGetState()
    state.mapSize = size
    state.setup()

    benchmark.identityGet(state, blackhole)
    addOperation("hppc_get", operations, state.map)
  }
}