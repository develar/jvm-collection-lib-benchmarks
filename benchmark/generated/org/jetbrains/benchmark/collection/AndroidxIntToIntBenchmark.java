package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class AndroidxIntToIntBenchmark {
  @State(Scope.Thread)
  public static class GetBenchmarkState extends BaseBenchmarkState {
    public androidx.collection.MutableIntIntMap map;
    int[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      int oneFailureOutOf = this.oneFailureOutOf;
      androidx.collection.MutableIntIntMap map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToInt(keys.length, loadFactor);
      for (int key : keys) {
        map.put(key + (key % oneFailureOutOf == 0 ? 1 : 0), key);
      }

      this.map = map;
      this.keys = keys;
    }
  }

  @Benchmark
  public Object get(GetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    int[] keys = state.keys;
    androidx.collection.MutableIntIntMap map = state.map;
    for (int key : keys) {
      result ^= map.getOrDefault(key, -1);
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public Object put(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableIntIntMap map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToInt(state.loadFactor);
    for (int key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map._size);
    for (int key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map._size);
    return map;
  }

  @Benchmark
  public Object remove(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    androidx.collection.MutableIntIntMap map = org.jetbrains.benchmark.collection.factory.AndroidxFactory.createIntToInt(state.loadFactor);
    int add = 0;
    int remove = 0;
    int[] keys = state.keys;
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