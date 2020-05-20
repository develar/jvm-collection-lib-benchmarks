package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class FastutilIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToIntBenchmark()
    addOperation("fastutil_get", operations, benchmark.get(setup(FastutilIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class FastutilIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    addOperation("fastutil_get", operations, benchmark.get(setup(FastutilIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class FastutilObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilIntToObjectBenchmark()
    addOperation("fastutil_get", operations, benchmark.objectGet(setup(FastutilIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("fastutil_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class FastutilObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilObjectToObjectBenchmark()
    addOperation("fastutil_get", operations, benchmark.get(setup(FastutilObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class FastutilReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = FastutilReferenceToObjectBenchmark()
    addOperation("fastutil_get", operations, benchmark.get(setup(FastutilReferenceToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("fastutil_put", operations, benchmark.put(state, blackhole))
    addOperation("fastutil_remove", operations, benchmark.remove(state, blackhole))
  }
}