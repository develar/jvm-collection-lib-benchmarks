package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class KolobokeIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToIntBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntPutOrRemoveBenchmarkState()
    setup(state, size)
    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: KolobokeIntToIntBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = KolobokeIntToIntBenchmark.GetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("koloboke_get", operations, state.map)
  }
}

internal class KolobokeIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: KolobokeIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = KolobokeIntToObjectBenchmark.IntToObjectGetBenchmarkState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("koloboke_get", operations, state.map)
  }
}

internal class KolobokeObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("koloboke_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.objectRemove(state, blackhole))
  }

  private fun measureGet(benchmark: KolobokeIntToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = KolobokeIntToObjectBenchmark.ObjectToIntGetBenchmarkState()
    setup(state, size)

    benchmark.objectGet(state, blackhole)
    addOperation("koloboke_get", operations, state.map)
  }
}

internal class KolobokeObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeObjectToObjectBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: KolobokeObjectToObjectBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = KolobokeObjectToObjectBenchmark.BenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("koloboke_get", operations, state.map)
  }
}

internal class KolobokeReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeReferenceToObjectMapBenchmark()
    measureGet(benchmark, size, operations, blackhole)

    val state = BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState()
    setup(state, size)

    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }

  private fun measureGet(benchmark: KolobokeReferenceToObjectMapBenchmark, size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val state = KolobokeReferenceToObjectMapBenchmark.IdentityBenchmarkGetState()
    setup(state, size)

    benchmark.get(state, blackhole)
    addOperation("koloboke_get", operations, state.map)
  }
}