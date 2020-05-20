package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class EcIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToIntBenchmark()
    addOperation("ec_get", operations, benchmark.get(setup(EcIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class EcIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToObjectBenchmark()
    addOperation("ec_get", operations, benchmark.get(setup(EcIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class EcObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcIntToObjectBenchmark()
    addOperation("ec_get", operations, benchmark.objectGet(setup(EcIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("ec_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("ec_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class EcObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcObjectToObjectBenchmark()
    addOperation("ec_get", operations, benchmark.get(setup(EcObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class EcReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = EcReferenceToObjectMapBenchmark()
    addOperation("ec_get", operations, benchmark.get(setup(EcReferenceToObjectMapBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("ec_put", operations, benchmark.put(state, blackhole))
    addOperation("ec_remove", operations, benchmark.remove(state, blackhole))
  }
}