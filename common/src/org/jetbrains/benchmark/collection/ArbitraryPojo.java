package org.jetbrains.benchmark.collection;

// https://github.com/austinv11/Long-Map-Benchmarks/blob/master/src/jmh/java/com/austinv11/bench/MapTests.java
public final class ArbitraryPojo {
  public int obj1;
  public int obj2;
  // used only for get tests
  public int index;

  // for jackson
  @SuppressWarnings("unused")
  private ArbitraryPojo() {
  }

  public ArbitraryPojo(int obj1, int obj2) {
    this.obj1 = obj1;
    this.obj2 = obj2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ArbitraryPojo that = (ArbitraryPojo) o;
    return obj1 == that.obj1 && obj2 == that.obj2 && index == that.index;
  }

  @Override
  public int hashCode() {
    int result = obj1;
    result = 31 * result + obj2;
    result = 31 * result + index;
    return result;
  }
}
