package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class TroveIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToIntBenchmark()
    addOperation("trove_get", operations, benchmark.get(setup(TroveIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToObjectBenchmark()
    addOperation("trove_get", operations, benchmark.get(setup(TroveIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveIntToObjectBenchmark()
    addOperation("trove_get", operations, benchmark.objectGet(setup(TroveIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("trove_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("trove_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class TroveObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveObjectToObjectBenchmark()
    addOperation("trove_get", operations, benchmark.get(setup(TroveObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveReferenceToObjectMapBenchmark()
    addOperation("trove_get", operations, benchmark.get(setup(TroveReferenceToObjectMapBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("trove_put", operations, benchmark.put(state, blackhole))
    addOperation("trove_remove", operations, benchmark.remove(state, blackhole))
  }
}