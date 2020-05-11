package org.jetbrains.benchmark.collection.factory;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

public final class TroveJbFactory {
  public static <K, V> THashMap<K, V> createReferenceToObject(int expectedSize) {
    return new THashMap<>(expectedSize, TObjectHashingStrategy.IDENTITY);
  }

  public static <K, V> THashMap<K, V> createReferenceToObject() {
    return new THashMap<>(TObjectHashingStrategy.IDENTITY);
  }
}
