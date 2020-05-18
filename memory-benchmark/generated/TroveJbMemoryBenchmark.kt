package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class TroveJbIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    setup(state, size)
    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveJbIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveJbIntToIntBenchmark.GetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("trove-jb_get", operations, state.map)
  }
}

internal class TroveJbIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveJbIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveJbIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("trove-jb_get", operations, state.map)
  }
}

internal class TroveJbObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("trove-jb_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveJbIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveJbIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    setup(state, size)

    benchmark.objectGet(state, blackhole)
    addOperation("trove-jb_get", operations, state.map)
  }
}

internal class TroveJbObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveJbObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveJbObjectToObjectBenchmark.BenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("trove-jb_get", operations, state.map)
  }
}

internal class TroveJbReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbReferenceToObjectMapBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: TroveJbReferenceToObjectMapBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = TroveJbReferenceToObjectMapBenchmark.IdentityBenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("trove-jb_get", operations, state.map)
  }
}