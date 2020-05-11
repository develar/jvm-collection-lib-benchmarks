package org.jetbrains.benchmark.collection.factory;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.IdentityHashingStrategy;

public final class TroveFactory {
  public static <K, V> TCustomHashMap<K, V> createReferenceToObject(int expectedSize) {
    return new TCustomHashMap<>(IdentityHashingStrategy.INSTANCE, expectedSize);
  }

  public static <K, V> TCustomHashMap<K, V> createReferenceToObject() {
    return new TCustomHashMap<>(IdentityHashingStrategy.INSTANCE);
  }
}
