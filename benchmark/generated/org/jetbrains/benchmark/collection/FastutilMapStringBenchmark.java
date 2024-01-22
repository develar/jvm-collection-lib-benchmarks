package org.jetbrains.benchmark.collection;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

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
public class FastutilMapStringBenchmark implements ObjectBenchmark<FastutilMapStringBenchmark.StringBenchmarkState> {

  private static final Hash.Strategy<String> STRING_STRATEGY = new Hash.Strategy<>() {
    @Override
    public int hashCode(String s) {
      return s == null ? 0 : s.hashCode();
    }

    @Override
    public boolean equals(String s, String k1) {
      return s == k1 || (s != null && k1 != null && s.hashCode() == k1.hashCode() && s.equals(k1));
    }
  };

  @State(Scope.Benchmark)
  public static class StringBenchmarkState extends BaseBenchmarkState {
    public it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<String, String> map;
    String[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int size = parseSize(mapSize);
      Object2ObjectOpenCustomHashMap<String, String> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<>(size, loadFactor, STRING_STRATEGY);
      this.keys = generateWords(size, map, oneFailureOutOf);
      this.map = map;
    }
  }

  @Override
  @Benchmark
  public Object get(StringBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    String[] keys = state.keys;
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<String, String> map = state.map;
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
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<String, String> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<>(0, state.loadFactor, STRING_STRATEGY);
    for (String key : state.keys) {
      map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @SuppressWarnings("DuplicatedCode")
  @Benchmark
  public Object remove(StringBenchmarkState state, Blackhole blackhole) {
    it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<String, String> map = new it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap<>(0, state.loadFactor, STRING_STRATEGY);
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