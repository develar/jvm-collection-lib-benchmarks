package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


@SuppressWarnings("DuplicatedCode")
public class FastutilIntToObjectBenchmark {
  @State(Scope.Thread)
  public static class IntToObjectGetBenchmarkState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap map;
    int[] keys;

    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      ArbitraryPojo[] values = Util.loadObjectArray(mapSize);
      it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap(keys.length);
      for (int i = 0, n = keys.length; i < n; i++) {
        int key = keys[i];
        map.put(key + (key % oneFailureOutOf == 0 ? 1 : 0), values[i]);
      }

      this.map = map;
      this.keys = keys;
    }
  }

  @State(Scope.Thread)
  public static class ObjectToIntGetBenchmarkState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap map;
    ArbitraryPojo[] keys;

    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      int[] values = Util.loadIntArray(mapSize);
      it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap(keys.length);
      for (int i = 0, l = keys.length; i < l; i++) {
        ArbitraryPojo key = keys[i];
        ArbitraryPojo newKey = new ArbitraryPojo(key.obj1, key.obj2);
        if (i % oneFailureOutOf == 0) {
          newKey.index = i;
        }
        map.put(newKey, values[i]);
      }

      this.keys = keys;
      this.map = map;
    }
  }

  @Benchmark
  public void get(IntToObjectGetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    int[] keys = state.keys;
    it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap map = state.map;
    for (int key : keys) {
      if (map.get(key) != null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
  }

  @Benchmark
  public it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap put(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap();
    int[] keys = state.keys;
    ArbitraryPojo[] values = state.values;
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map.size());
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap remove(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap();
    int add = 0;
    int remove = 0;
    int[] keys = state.keys;
    ArbitraryPojo[] values = state.values;
    while (add < keys.length) {
      map.put(keys[add], values[add]);
      add++;
      map.put(keys[add], values[add]);
      add++;
      map.remove(keys[remove++]);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public void objectGet(ObjectToIntGetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap map = state.map;
    for (ArbitraryPojo key : keys) {
      result ^= map.getInt(key);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap objectPut(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap();
    ArbitraryPojo[] keys = state.keys;
    ArbitraryPojo[] keys2 = state.keys2;
    int[] values = state.values;
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map.size());
    for (int i = 0, n = keys2.length; i < n; i++) {
      map.put(keys2[i], values[i]);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap objectRemove(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap<ArbitraryPojo> map = new it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap();
    int add = 0;
    int remove = 0;
    ArbitraryPojo[] keys = state.keys;
    ArbitraryPojo[] keys2 = state.keys2;
    int[] values = state.values;
    while (add < keys.length) {
      map.put(keys[add], values[add]);
      add++;
      map.put(keys[add], values[add]);
      add++;
      map.removeInt(keys2[remove++]);
    }
    blackhole.consume(map.size());
    return map;
  }
}