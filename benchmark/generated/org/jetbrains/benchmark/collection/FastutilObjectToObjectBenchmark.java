package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


/**
 * “Get” test: Populate a map with a pre-generated set of keys (in the JMH setup), make ~50% successful and ~50% unsuccessful “get” calls.
 * For non-identity maps with object keys we use a distinct set of keys (the different object with the same value is used for successful “get” calls).

 * "Put/update" test: Add a pre-generated set of keys to the map.
 * In the second loop add the equal set of keys (different objects with the same values) to this map again (make the updates).
 *
 * “Put/remove” test: In a loop: add 2 entries to a map, remove 1 of existing entries (“add” pointer is increased by 2 on each iteration, “remove” pointer is increased by 1).
 */
public class FastutilObjectToObjectBenchmark {
  @State(Scope.Thread)
  public static class BenchmarkGetState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(keys.length, loadFactor);
      for (int i = 0, l = keys.length; i < l; i++) {
        // for non-identity maps with object keys we use a distinct set of keys (the different object with the same value is used for successful “get” calls).
        ArbitraryPojo key = keys[i];
        ArbitraryPojo newKey = new ArbitraryPojo(key.obj1, key.obj2);
        if (i % oneFailureOutOf == 0) {
          newKey.index = i;
        }
        map.put(newKey, key);
      }

      this.keys = keys;
      this.map = map;
    }
  }

  @State(Scope.Thread)
  public static class IdentityBenchmarkGetState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

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

  @Benchmark
  public void get(BenchmarkGetState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      ArbitraryPojo key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> put(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(0, state.loadFactor);
    for (ArbitraryPojo key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    for (ArbitraryPojo key : state.keys2) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> remove(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap<>(0, state.loadFactor);
    int add = 0;
    int remove = 0;
    ArbitraryPojo[] keys = state.keys;
    ArbitraryPojo[] keys2 = state.keys2;
    while (add < keys.length) {
      map.put(keys[add], keys[add]);
      add++;
      map.put(keys[add], keys[add]);
      add++;
      map.remove(keys2[remove++]);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public void identityGet(IdentityBenchmarkGetState state, Blackhole blackhole) {
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
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> identityPut(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
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

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap<ArbitraryPojo, ArbitraryPojo> identityRemove(BaseBenchmarkState.ReferencePutOrRemoveBenchmarkState state, Blackhole blackhole) {
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