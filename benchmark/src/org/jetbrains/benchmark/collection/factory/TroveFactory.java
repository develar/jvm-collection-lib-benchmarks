package org.jetbrains.benchmark.collection.factory;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.IdentityHashingStrategy;

public final class TroveFactory {
  public static <K, V> TCustomHashMap<K, V> createReferenceToObject(int expectedSize, float loadFactor) {
    return new TCustomHashMap<>(IdentityHashingStrategy.INSTANCE, expectedSize, loadFactor);
  }

  public static <K, V> TCustomHashMap<K, V> createReferenceToObject(float loadFactor) {
    return new TCustomHashMap<>(IdentityHashingStrategy.INSTANCE, -1, loadFactor);
  }
}
