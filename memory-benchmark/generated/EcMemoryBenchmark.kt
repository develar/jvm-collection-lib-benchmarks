package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class EcIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: EcIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = EcIntToIntBenchmark.GetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("ec_get", operations, state.map)
  }
}

internal class EcIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: EcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = EcIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("ec_get", operations, state.map)
  }
}

internal class EcObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("ec_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("ec_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: EcIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = EcIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.objectGet(state, blackhole)
    addOperation("ec_get", operations, state.map)
  }
}

internal class EcObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: EcObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = EcObjectToObjectBenchmark.BenchmarkGetState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("ec_get", operations, state.map)
  }
}

internal class EcReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("ec_put", operations, benchmark.identityPut(state, blackhole))
    addOperation("ec_remove", operations, benchmark.identityRemove(state, blackhole))
  }

  private fun measureGet(benchmark: EcObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = EcObjectToObjectBenchmark.IdentityBenchmarkGetState()
    state.mapSize = size
    state.setup()

    benchmark.identityGet(state, blackhole)
    addOperation("ec_get", operations, state.map)
  }
}