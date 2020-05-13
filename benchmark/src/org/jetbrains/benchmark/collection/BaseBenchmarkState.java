package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public abstract class BaseBenchmarkState {
  @Param("1K")
  public String mapSize;

  @Param("0.75")
  public float loadFactor;

  @Param("2")
  public int oneFailureOutOf;

  @State(Scope.Thread)
  public static class ReferencePutOrRemoveBenchmarkState extends BaseBenchmarkState {
    ArbitraryPojo[] keys;

    @Setup
    public void setup() throws Exception {
      keys = Util.loadObjectArray(mapSize);
    }
  }

  @State(Scope.Thread)
  public static class ObjectPutOrRemoveBenchmarkState extends BaseBenchmarkState {
    ArbitraryPojo[] keys;
    ArbitraryPojo[] keys2;

    @Setup
    public void setup() throws Exception {
      keys = Util.loadObjectArray(mapSize);
      keys2 = new ArbitraryPojo[keys.length];
      for (int i = 0; i < keys.length; i++) {
        ArbitraryPojo key = keys[i];
        keys2[i] = new ArbitraryPojo(key.obj1, key.obj2);
      }
    }
  }

  @State(Scope.Thread)
  public static class IntPutOrRemoveBenchmarkState extends BaseBenchmarkState {
    int[] keys;

    @Setup
    public void setup() throws Exception {
      keys = Util.loadIntArray(mapSize);
    }
  }

  @State(Scope.Thread)
  public static class IntToObjectPutOrRemoveBenchmarkState extends BaseBenchmarkState {
    int[] keys;
    ArbitraryPojo[] values;

    @Setup
    public void setup() throws Exception {
      keys = Util.loadIntArray(mapSize);
      values = Util.loadObjectArray(mapSize);
    }
  }

  @State(Scope.Thread)
  public static class ObjectToIntPutOrRemoveBenchmarkState extends BaseBenchmarkState {
    ArbitraryPojo[] keys;
    ArbitraryPojo[] keys2;
    int[] values;

    @Setup
    public void setup() throws Exception {
      keys = Util.loadObjectArray(mapSize);
      values = Util.loadIntArray(mapSize);

      keys2 = new ArbitraryPojo[keys.length];
      for (int i = 0; i < keys.length; i++) {
        ArbitraryPojo key = keys[i];
        keys2[i] = new ArbitraryPojo(key.obj1, key.obj2);
      }
    }
  }
}
