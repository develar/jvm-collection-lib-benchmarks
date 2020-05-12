package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


public class EcIntToIntBenchmark {
  @State(Scope.Thread)
  public static class BenchmarkState extends BaseBenchmarkState {
    public org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap map;
    int[] keys;

    @Setup
    public void setup() throws Exception {
      int[] keys = Util.loadIntArray(mapSize);
      org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap map = new org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap(keys.length);
      for (int key : keys) {
        map.put(key + (key % oneFailureOutOf == 0 ? 1 : 0), key);
      }

      this.map = map;
      this.keys = keys;
    }
  }

  @Benchmark
  public void get(BenchmarkState state, Blackhole blackhole) {
    int result = 0;
    int[] keys = state.keys;
    org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap map = state.map;
    for (int key : keys) {
      result ^= map.get(key);
    }
    blackhole.consume(result);
  }

  @Benchmark
  public org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap put(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap map = new org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap();
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
  public org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap remove(BaseBenchmarkState.IntPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap map = new org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap();
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