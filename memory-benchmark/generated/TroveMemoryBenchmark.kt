package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class TroveIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveIntToIntBenchmark.BenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("trove_get", operations, state.map)
  }
}

internal class TroveIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("trove_get", operations, state.map)
  }
}

internal class TroveObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("trove_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("trove_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.objectGet(state, blackhole)
    addOperation("trove_get", operations, state.map)
  }
}

internal class TroveObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveObjectToObjectBenchmark.BenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("trove_get", operations, state.map)
  }
}

internal class TroveReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("trove_put", operations, benchmark.identityPut(state, blackhole))
    addOperation("trove_remove", operations, benchmark.identityRemove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveObjectToObjectBenchmark.IdentityBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.identityGet(state, blackhole)
    addOperation("trove_get", operations, state.map)
  }
}