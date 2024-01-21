package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class AndroidxReferenceToObjectBenchmark implements ObjectBenchmark<AndroidxReferenceToObjectBenchmark.BenchmarkGetState> {
  @State(Scope.Thread)
  public static class BenchmarkGetState extends BaseBenchmarkState {
    public androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createReferenceToObject(keys.length, loadFactor);
      for (int i = 0, l = keys.length; i < l; i++) {
        ArbitraryPojo key = keys[i];
        ArbitraryPojo newKey;
        if (i % oneFailureOutOf == 0) {
          newKey = new ArbitraryPojo(key.obj1, key.obj2);
          newKey.index = i;
        }
        else {
          newKey = key;
        }
        map.put(newKey, key);
      }

      this.map = map;
      this.keys = keys;
    }
  }

  @Override
  @Benchmark
  public Object get(BenchmarkGetState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> map = state.map;
    for (int i = 0, l = map._size; i < l; i++) {
      ArbitraryPojo key = keys[i];
      if (map.getOrDefault(key, null) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> put(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createReferenceToObject(state.loadFactor);
    ArbitraryPojo[] keys = state.keys;
    for (ArbitraryPojo key : keys) {
      map.put(key, key);
    }
    blackhole.consume(map._size);
    // same keys are use for identity test
    for (ArbitraryPojo key : keys) {
      map.put(key, key);
    }
    blackhole.consume(map._size);
    return map;
  }

  @SuppressWarnings("DuplicatedCode")
  @Benchmark
  public androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> remove(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableScatterMap<ArbitraryPojo, ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createReferenceToObject(state.loadFactor);
    int add = 0;
    int remove = 0;
    ArbitraryPojo[] keys = state.keys;
    while (add < keys.length) {
      map.put(keys[add], keys[add]);
      ++add;
      map.put(keys[add], keys[add]);
      ++add;
      map.remove(keys[remove++]);
    }
    blackhole.consume(map._size);
    return map;
  }
}