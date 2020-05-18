package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class HppcReferenceToObjectMapBenchmark {
  @State(Scope.Thread)
  public static class IdentityBenchmarkGetState extends BaseBenchmarkState {
    public com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectObjectIdentityHashMap<>(keys.length);
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

  @Benchmark
  public void get(IdentityBenchmarkGetState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      ArbitraryPojo key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
  }

  @Benchmark
  public com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> put(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectObjectIdentityHashMap<>();
    ArbitraryPojo[] keys = state.keys;
    for (ArbitraryPojo key : keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    // same keys are use for identity test
    for (ArbitraryPojo key : keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @SuppressWarnings("DuplicatedCode")
  @Benchmark
  public com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> remove(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.ObjectObjectIdentityHashMap<ArbitraryPojo, ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectObjectIdentityHashMap<>();
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
    blackhole.consume(map.size());
    return map;
  }
}