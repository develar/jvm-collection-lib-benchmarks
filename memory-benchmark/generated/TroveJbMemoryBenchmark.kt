package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class TroveJbIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToIntBenchmark()
    addOperation("trove-jb_get", operations, benchmark.get(setup(TroveJbIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveJbIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToObjectBenchmark()
    addOperation("trove-jb_get", operations, benchmark.get(setup(TroveJbIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveJbObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbIntToObjectBenchmark()
    addOperation("trove-jb_get", operations, benchmark.objectGet(setup(TroveJbIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("trove-jb_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class TroveJbObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbObjectToObjectBenchmark()
    addOperation("trove-jb_get", operations, benchmark.get(setup(TroveJbObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class TroveJbReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = TroveJbReferenceToObjectBenchmark()
    addOperation("trove-jb_get", operations, benchmark.get(setup(TroveJbReferenceToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("trove-jb_put", operations, benchmark.put(state, blackhole))
    addOperation("trove-jb_remove", operations, benchmark.remove(state, blackhole))
  }
}