package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Objects;

public class IntToIntBenchmark {
  @State(Scope.Thread)
  public static class GetBenchmarkState extends BaseBenchmarkState {
    public HashMap<Integer, Integer> map;
    int[] keys;

    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      int oneFailureOutOf = this.oneFailureOutOf;
      HashMap<Integer, Integer> map = new HashMap<>(keys.length, loadFactor);
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
    HashMap<Integer, Integer> map = state.map;
    for (int key : keys) {
      result ^= Objects.requireNonNullElse(map.get(key), -1);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public HashMap<Integer, Integer> put(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    HashMap<Integer, Integer> map = new HashMap<>(0, state.loadFactor);
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
  public HashMap<Integer, Integer> remove(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    HashMap<Integer, Integer> map = new HashMap<>(0, state.loadFactor);
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