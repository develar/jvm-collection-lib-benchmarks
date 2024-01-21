package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


@SuppressWarnings("DuplicatedCode")
public class AndroidxIntToObjectBenchmark {
  @State(Scope.Thread)
  public static class IntToObjectGetBenchmarkState extends BaseBenchmarkState {
    public androidx.collection.MutableIntObjectMap<ArbitraryPojo> map;
    int[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      ArbitraryPojo[] values = Util.loadObjectArray(mapSize);
      androidx.collection.MutableIntObjectMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToObject(keys.length, loadFactor);
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
    public androidx.collection.MutableObjectIntMap<ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);
      int[] values = Util.loadIntArray(mapSize);
      androidx.collection.MutableObjectIntMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createObjectToInt(keys.length, loadFactor);
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
  public Object get(IntToObjectGetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    int[] keys = state.keys;
    androidx.collection.MutableIntObjectMap<ArbitraryPojo> map = state.map;
    for (int key : keys) {
      if (map.getOrDefault(key, null) != null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public androidx.collection.MutableIntObjectMap<ArbitraryPojo> put(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableIntObjectMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToObject(state.loadFactor);
    int[] keys = state.keys;
    ArbitraryPojo[] values = state.values;
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map._size);
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map._size);
    return map;
  }

  @Benchmark
  public Object remove(BaseBenchmarkState.IntToObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableIntObjectMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToObject(state.loadFactor);
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
    blackhole.consume(map._size);
    return map;
  }

  @Benchmark
  public Object objectGet(ObjectToIntGetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    androidx.collection.MutableObjectIntMap<ArbitraryPojo> map = state.map;
    for (ArbitraryPojo key : keys) {
      result ^= map.getOrDefault(key, -1);
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public Object objectPut(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableObjectIntMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createObjectToInt(state.loadFactor);
    ArbitraryPojo[] keys = state.keys;
    ArbitraryPojo[] keys2 = state.keys2;
    int[] values = state.values;
    for (int i = 0, n = keys.length; i < n; i++) {
      map.put(keys[i], values[i]);
    }
    blackhole.consume(map._size);
    for (int i = 0, n = keys2.length; i < n; i++) {
      map.put(keys2[i], values[i]);
    }
    blackhole.consume(map._size);
    return map;
  }

  @Benchmark
  public androidx.collection.MutableObjectIntMap<ArbitraryPojo> objectRemove(BaseBenchmarkState.ObjectToIntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableObjectIntMap<ArbitraryPojo> map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createObjectToInt(state.loadFactor);
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
    blackhole.consume(map._size);
    return map;
  }
}