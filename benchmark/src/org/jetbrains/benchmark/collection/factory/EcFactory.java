package org.jetbrains.benchmark.collection.factory;

import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;

public final class EcFactory {
  public static <K, V> UnifiedMapWithHashingStrategy<K, V> createReferenceToObject(int expectedSize, float loadFactor) {
    return new UnifiedMapWithHashingStrategy<>(HashingStrategies.identityStrategy(), expectedSize, loadFactor);
  }

  public static <K, V> UnifiedMapWithHashingStrategy<K, V> createReferenceToObject(float loadFactor) {
    return new UnifiedMapWithHashingStrategy<>(HashingStrategies.identityStrategy(), 0, loadFactor);
  }
}
