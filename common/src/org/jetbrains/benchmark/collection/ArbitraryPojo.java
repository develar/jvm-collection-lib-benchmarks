package org.jetbrains.benchmark.collection;

import java.util.Objects;

// https://github.com/austinv11/Long-Map-Benchmarks/blob/master/src/jmh/java/com/austinv11/bench/MapTests.java
public final class ArbitraryPojo {
  public String obj1;
  public int obj2;
  // used only for get tests
  public int index;

  // for jackson
  @SuppressWarnings("unused")
  private ArbitraryPojo() {
  }

  public ArbitraryPojo(String obj1, int obj2) {
    this.obj1 = obj1;
    this.obj2 = obj2;
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(obj1);
    result = 31 * result + obj2;
    result = 31 * result + index;
    return result;
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
    return Objects.equals(obj1, that.obj1) && obj2 == that.obj2 && index == that.index;
  }
}
