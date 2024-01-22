package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;

import static org.jetbrains.benchmark.collection.Util.generateWords;
import static org.jetbrains.benchmark.collection.Util.parseSize;

/**
 * “Get” test: Populate a map with a pre-generated set of keys (in the JMH setup), make ~50% successful and ~50% unsuccessful “get” calls.
 * For non-identity maps with object keys, we use a distinct set of keys (the different object with the same value is used for successful “get” calls).

 * "Put/update" test: Add a pre-generated set of keys to the map.
 * In the second loop, add the equal set of keys (different objects with the same values) to this map again (make the updates).
 * <p>
 * “Put/remove” test: In a loop: add two entries to a map, remove one of the existing entries (“add” pointer is increased by 2 in each iteration, “remove” pointer is increased by 1).
 */
@SuppressWarnings("DuplicatedCode")
public class HashMapStringBenchmark implements ObjectBenchmark<HashMapStringBenchmark.StringBenchmarkState> {
  @State(Scope.Benchmark)
  public static class StringBenchmarkState extends BaseBenchmarkState {
    public HashMap<String, String> map;
    String[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int size = parseSize(mapSize);
      HashMap<String, String> map = new HashMap<>(size, loadFactor);
      this.keys = generateWords(size, map, oneFailureOutOf);
      this.map = map;
    }
  }

  @Override
  @Benchmark
  public Object get(StringBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    String[] keys = state.keys;
    HashMap<String, String> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      String key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public Object put(StringBenchmarkState state, Blackhole blackhole) {
    HashMap<String, String> map = new HashMap<>(0, state.loadFactor);
    for (String key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @SuppressWarnings("DuplicatedCode")
  @Benchmark
  public Object remove(StringBenchmarkState state, Blackhole blackhole) {
    HashMap<String, String> map = new HashMap<>(0, state.loadFactor);
    int add = 0;
    int remove = 0;
    String[] keys = state.keys;
    while (add < keys.length) {
      map.put(keys[add], keys[add]);
      add++;
      map.put(keys[add], keys[add]);
      add++;
      map.remove(keys[remove++]);
    }
    blackhole.consume(map.size());
    return map;
  }
}