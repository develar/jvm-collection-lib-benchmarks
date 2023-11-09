package org.jetbrains.benchmark.collection;

import kotlin.Unit;
import kotlinx.collections.immutable.PersistentMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import static kotlinx.collections.immutable.ExtensionsKt.mutate;
import static kotlinx.collections.immutable.ExtensionsKt.persistentHashMapOf;

@SuppressWarnings("DuplicatedCode")
public class KotlinxObjectToObjectBenchmark implements ObjectBenchmark<KotlinxObjectToObjectBenchmark.BenchmarkGetState> {
  @State(Scope.Thread)
  public static class BenchmarkGetState extends BaseBenchmarkState {
    public PersistentMap<ArbitraryPojo, ArbitraryPojo> map;
    ArbitraryPojo[] keys;

    @Override
    @Setup
    public void setup() throws Exception {
      ArbitraryPojo[] keys = Util.loadObjectArray(mapSize);

      this.keys = keys;
      this.map = mutate(persistentHashMapOf(), map -> {
        for (int i = 0, l = keys.length; i < l; i++) {
          // for non-identity maps with object keys, we use a distinct set of keys (the different object with the same value is used for successful “get” calls).
          ArbitraryPojo key = keys[i];
          ArbitraryPojo newKey = new ArbitraryPojo(key.obj1, key.obj2);
          if (i % oneFailureOutOf == 0) {
            newKey.index = i;
          }
          map.put(newKey, key);
        }
        return Unit.INSTANCE;
      });
    }
  }

  @Override
  @Benchmark
  public Object get(BenchmarkGetState state, Blackhole blackhole) {
    int result = 0;
    ArbitraryPojo[] keys = state.keys;
    PersistentMap<ArbitraryPojo, ArbitraryPojo> map = state.map;
    for (int i = 0, l = map.size(); i < l; i++) {
      ArbitraryPojo key = keys[i];
      if (map.get(key) == null) {
        result ^= 1;
      }
    }
    blackhole.consume(result);
    return map;
  }

  @Benchmark
  public Object put(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    PersistentMap<ArbitraryPojo, ArbitraryPojo> map = persistentHashMapOf();
    for (ArbitraryPojo key : state.keys) {
      map = map.put(key, key);
    }
    blackhole.consume(map.size());
    for (ArbitraryPojo key : state.keys2) {
      map = map.put(key, key);
    }
    blackhole.consume(map.size());
    return map;
  }

  @SuppressWarnings("DuplicatedCode")
  @Benchmark
  public Object remove(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState state, Blackhole blackhole) {
    PersistentMap<ArbitraryPojo, ArbitraryPojo> map = persistentHashMapOf();
    int add = 0;
    int remove = 0;
    ArbitraryPojo[] keys = state.keys;
    ArbitraryPojo[] keys2 = state.keys2;
    while (add < keys.length) {
      map = map.put(keys[add], keys[add]);
      add++;
      map = map.put(keys[add], keys[add]);
      add++;
      map = map.remove(keys2[remove++]);
    }
    blackhole.consume(map.size());
    return map;
  }
}