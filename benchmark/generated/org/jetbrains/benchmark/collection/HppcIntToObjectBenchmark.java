package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;



@SuppressWarnings("DuplicatedCode")
public class HppcIntToObjectBenchmark {
  @State(Scope.Thread)
  public static class IntToObjectGetBenchmarkState extends BaseBenchmarkState {
    com.carrotsearch.hppc.IntObjectHashMap map;
    int[] keys;

    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      ArbitraryPojo[] values = Util.loadObjectArray(mapSize);
      com.carrotsearch.hppc.IntObjectHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.IntObjectHashMap(keys.length);
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
    com.carrotsearch.hppc.ObjectIntHashMap map;
    ArbitraryPojo[] keys;

    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      int[] values = Util.loadIntArray(mapSize);
      com.carrotsearch.hppc.ObjectIntHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectIntHashMap(keys.length);
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
    com.carrotsearch.hppc.IntObjectHashMap map = state.map;
    for (int key : keys) {
      if (map.get(key) != null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
  }

  @Benchmark
  public void put(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.IntObjectHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.IntObjectHashMap();
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
  }

  @Benchmark
  public void remove(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.IntObjectHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.IntObjectHashMap();
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
  }

  @Benchmark
  public void objectGet(ObjectToIntGetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    com.carrotsearch.hppc.ObjectIntHashMap map = state.map;
    for (ArbitraryPojo key : keys) {
      result ^= map.get(key);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public void objectPut(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.ObjectIntHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectIntHashMap();
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
  }

  @Benchmark
  public void objectRemove(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.carrotsearch.hppc.ObjectIntHashMap<ArbitraryPojo> map = new com.carrotsearch.hppc.ObjectIntHashMap();
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
      map.remove(keys2[remove++]); // removeInt
    }
    blackhole.consume(map.size());
  }
}