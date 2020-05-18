package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class FastutilIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    setup(state, size)
    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToIntBenchmark.GetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("fastutil_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    setup(state, size)

    benchmark.objectGet(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilObjectToObjectBenchmark.BenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}

internal class FastutilReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilReferenceToObjectMapBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: FastutilReferenceToObjectMapBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = FastutilReferenceToObjectMapBenchmark.IdentityBenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("fastutil_get", operations, state.map)
  }
}