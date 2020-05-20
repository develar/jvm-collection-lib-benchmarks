package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class KolobokeIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToIntBenchmark()
    addOperation("koloboke_get", operations, benchmark.get(setup(KolobokeIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class KolobokeIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToObjectBenchmark()
    addOperation("koloboke_get", operations, benchmark.get(setup(KolobokeIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class KolobokeObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeIntToObjectBenchmark()
    addOperation("koloboke_get", operations, benchmark.objectGet(setup(KolobokeIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("koloboke_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class KolobokeObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeObjectToObjectBenchmark()
    addOperation("koloboke_get", operations, benchmark.get(setup(KolobokeObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class KolobokeReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KolobokeReferenceToObjectMapBenchmark()
    addOperation("koloboke_get", operations, benchmark.get(setup(KolobokeReferenceToObjectMapBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("koloboke_put", operations, benchmark.put(state, blackhole))
    addOperation("koloboke_remove", operations, benchmark.remove(state, blackhole))
  }
}