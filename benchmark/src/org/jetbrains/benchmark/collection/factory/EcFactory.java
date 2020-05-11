package org.jetbrains.benchmark.collection.factory;

import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;

public final class EcFactory {
  public static <K, V> UnifiedMapWithHashingStrategy<K, V> createReferenceToObject(int expectedSize) {
    return new UnifiedMapWithHashingStrategy<>(HashingStrategies.identityStrategy(), expectedSize);
  }

  public static <K, V> UnifiedMapWithHashingStrategy<K, V> createReferenceToObject() {
    return new UnifiedMapWithHashingStrategy<>(HashingStrategies.identityStrategy());
  }
}
