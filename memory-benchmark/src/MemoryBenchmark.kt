package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole

internal class IntToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToIntBenchmark()
    addOperation("java_get", operations, benchmark.get(setup(IntToIntBenchmark.GetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntPutOrRemoveBenchmarkState(), size)
    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class IntToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToObjectBenchmark()
    addOperation("java_get", operations, benchmark.get(setup(IntToObjectBenchmark.IntToObjectGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState(), size)
    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class ObjectToIntMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = IntToObjectBenchmark()
    addOperation("java_get", operations, benchmark.objectGet(setup(IntToObjectBenchmark.ObjectToIntGetBenchmarkState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState(), size)
    addOperation("java_put", operations, benchmark.objectPut(state, blackhole))
    addOperation("java_remove", operations, benchmark.objectRemove(state, blackhole))
  }
}

internal class ObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = ObjectToObjectBenchmark()
    addOperation("java_get", operations, benchmark.get(setup(ObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class KotlinObjectToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = KotlinxObjectToObjectBenchmark()
    addOperation("kotlin_get", operations, benchmark.get(setup(KotlinxObjectToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState(), size)
    addOperation("kotlin_put", operations, benchmark.put(state, blackhole))
    addOperation("kotlin_remove", operations, benchmark.remove(state, blackhole))
  }
}

internal class ReferenceToObjectMemoryBenchmark : Measurer {
  override fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole) {
    val benchmark = ReferenceToObjectBenchmark()
    addOperation("java_get", operations, benchmark.get(setup(ReferenceToObjectBenchmark.BenchmarkGetState(), size), blackhole))

    val state = setup(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState(), size)
    addOperation("java_put", operations, benchmark.put(state, blackhole))
    addOperation("java_remove", operations, benchmark.remove(state, blackhole))
  }
}