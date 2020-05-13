package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class FastutilIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToIntBenchmark.BenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("fastutil_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.objectGet(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilObjectToObjectBenchmark.BenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    state.mapSize = size
    state.setup()

    addOperation("fastutil_put", operations, benchmark.identityPut(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.identityRemove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilObjectToObjectBenchmark.IdentityBenchmarkState()
    state.mapSize = size
    state.setup()

    benchmark.identityGet(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}