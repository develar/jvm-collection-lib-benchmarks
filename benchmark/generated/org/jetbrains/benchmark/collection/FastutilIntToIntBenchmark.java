package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class FastutilIntToIntBenchmark {
  @State(Scope.Thread)
  public static class GetBenchmarkState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap map;
    int[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      int oneFailureOutOf = this.oneFailureOutOf;
      it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap map = new it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap(keys.length, loadFactor);
      for (int key : keys) {
        map.put(key + (key % oneFailureOutOf == 0 ? 1 : 0), key);
      }

      this.map = map;
      this.keys = keys;
    }
  }

  @Benchmark
  public void get(GetBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    int[] keys = state.keys;
    it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap map = state.map;
    for (int key : keys) {
      result ^= map.get(key);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap put(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap map = new it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap(0, state.loadFactor);
    for (int key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    for (int key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @Benchmark
  public it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap remove(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap map = new it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap(0, state.loadFactor);
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
    blackhole.consume(map.size());
    return map;
  }
}