package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class HppcIntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToIntBenchmark()
    addOperation("hppc_get", operations, benchmark.get(setup(HppcIntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class HppcIntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    addOperation("hppc_get", operations, benchmark.get(setup(HppcIntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class HppcObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcIntToObjectBenchmark()
    addOperation("hppc_get", operations, benchmark.objectGet(setup(HppcIntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("hppc_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class HppcObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcObjectToObjectBenchmark()
    addOperation("hppc_get", operations, benchmark.get(setup(HppcObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class HppcReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = HppcReferenceToObjectBenchmark()
    addOperation("hppc_get", operations, benchmark.get(setup(HppcReferenceToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("hppc_put", operations, benchmark.put(state, blackhole))
    addOperation("hppc_remove", operations, benchmark.remove(state, blackhole))
  }
}