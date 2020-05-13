package org.jetbrains.benchmark.collection.factory;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

public final class TroveJbFactory {
  public static <K, V> THashMap<K, V> createReferenceToObject(int expectedSize, float loadFactor) {
    return new THashMap<>(expectedSize, loadFactor, TObjectHashingStrategy.IDENTITY);
  }

  public static <K, V> THashMap<K, V> createReferenceToObject(float loadFactor) {
    return new THashMap<>(-1, loadFactor, TObjectHashingStrategy.IDENTITY);
  }
}
