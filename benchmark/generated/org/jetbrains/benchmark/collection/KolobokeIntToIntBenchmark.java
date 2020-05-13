package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class KolobokeIntToIntBenchmark {
  @State(Scope.Thread)
  public static class GetBenchmarkState extends BaseBenchmarkState {
    public com.koloboke.collect.map.hash.HashIntIntMap map;
    int[] keys;

    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      int oneFailureOutOf = this.oneFailureOutOf;
      com.koloboke.collect.map.hash.HashIntIntMap map = org.jetbrains.benchmark.collection.factory.KolobokeFactory.createIntToInt(keys.length, loadFactor);
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
    com.koloboke.collect.map.hash.HashIntIntMap map = state.map;
    for (int key : keys) {
      result ^= map.get(key);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public com.koloboke.collect.map.hash.HashIntIntMap put(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.koloboke.collect.map.hash.HashIntIntMap map = org.jetbrains.benchmark.collection.factory.KolobokeFactory.createIntToInt(state.loadFactor);
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
  public com.koloboke.collect.map.hash.HashIntIntMap remove(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    com.koloboke.collect.map.hash.HashIntIntMap map = org.jetbrains.benchmark.collection.factory.KolobokeFactory.createIntToInt(state.loadFactor);
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