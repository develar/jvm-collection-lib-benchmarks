package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class AndroidxIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = AndroidxIntToIntBenchmark()
    addOperation("androidx_get", operations, benchmark.get(setup(AndroidxIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("androidx_put", operations, benchmark.put(state, blackhole))
    addOperation("androidx_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class AndroidxIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = AndroidxIntToObjectBenchmark()
    addOperation("androidx_get", operations, benchmark.get(setup(AndroidxIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("androidx_put", operations, benchmark.put(state, blackhole))
    addOperation("androidx_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class AndroidxObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = AndroidxIntToObjectBenchmark()
    addOperation("androidx_get", operations, benchmark.objectGet(setup(AndroidxIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("androidx_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("androidx_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class AndroidxObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = AndroidxObjectToObjectBenchmark()
    addOperation("androidx_get", operations, benchmark.get(setup(AndroidxObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("androidx_put", operations, benchmark.put(state, blackhole))
    addOperation("androidx_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class AndroidxReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = AndroidxReferenceToObjectBenchmark()
    addOperation("androidx_get", operations, benchmark.get(setup(AndroidxReferenceToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("androidx_put", operations, benchmark.put(state, blackhole))
    addOperation("androidx_remove", operations, benchmark.remove(state, blackhole))
  }
}