package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class FastutilReferenceToObjectBenchmark implements ObjectBenchmark<FastutilReferenceToObjectBenchmark.BenchmarkGetState> {
  @State(Scope.Thread)
  public static class BenchmarkGetState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<>(keys.length);
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
    it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      ArbitraryPojo key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> put(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<>();
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
  public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> remove(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<>();
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