package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;

import static org.jetbrains.benchmark.collection.Util.generateWords;
import static org.jetbrains.benchmark.collection.Util.parseSize;

/**
 * “Get” test: Populate a map with a pre-generated set of keys (in the JMH setup), make ~50% successful and ~50% unsuccessful “get” calls.
 * For non-identity maps with object keys we use a distinct set of keys (the different object with the same value is used for successful “get” calls).

 * "Put/update" test: Add a pre-generated set of keys to the map.
 * In the second loop, add the equal set of keys (different objects with the same values) to this map again (make the updates).
 * <p>
 * “Put/remove” test: In a loop: add 2 entries to a map, remove 1 of existing entries (“add” pointer is increased by 2 on each iteration, “remove” pointer is increased by 1).
 */
@SuppressWarnings("DuplicatedCode")
public class JavaImmutableMapStringBenchmark implements ObjectBenchmark<JavaImmutableMapStringBenchmark.StringBenchmarkState> {
  @State(Scope.Benchmark)
  public static class StringBenchmarkState extends BaseBenchmarkState {
    public Map<String, String> map;
    String[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      int size = parseSize(mapSize);
      HashMap<String, String> map = new HashMap<>(size);
      this.keys = generateWords(size, map, oneFailureOutOf);
      this.map = Map.copyOf(map);
    }
  }

  @Override
  @Benchmark
  public Object get(StringBenchmarkState state, Blackhole blackhole) {
    int result = 0;
    String[] keys = state.keys;
    Map<String, String> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      String key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }
}