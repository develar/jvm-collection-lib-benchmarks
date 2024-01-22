package org.jetbrains.benchmark.collection.factory;

import androidx.collection.MutableIntIntMap;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;

public class AndroidxFactory {
  public static <K, V> MutableScatterMap<K, V> createReferenceToObject(int expectedSize, float loadFactor) {
    return new MutableScatterMap<>(expectedSize);
  }

  public static <K, V> MutableScatterMap<K, V> createReferenceToObject(float loadFactor) {
    return new MutableScatterMap<>();
  }

  public static <K, V> MutableScatterMap<K, V> createObjectToObject(int expectedSize, float loadFactor) {
    return new MutableScatterMap<>(expectedSize);
  }

  public static <K, V> MutableScatterMap<K, V> createObjectToObject(float loadFactor) {
    return new MutableScatterMap<>();
  }
  
  public static MutableIntIntMap createIntToInt(float loadFactor) {
    return new MutableIntIntMap();
  }

  public static MutableIntIntMap createIntToInt(int expectedSize, float loadFactor) {
    return new MutableIntIntMap(expectedSize);
  }

  public static <K> MutableIntObjectMap<K> createIntToObject(float loadFactor) {
    return new MutableIntObjectMap<>();
  }

  public static <K> MutableIntObjectMap<K> createIntToObject(int expectedSize, float loadFactor) {
    return new MutableIntObjectMap<>(expectedSize);
  }

  public static <K> MutableObjectIntMap<K> createObjectToInt(float loadFactor) {
    return new MutableObjectIntMap<>();
  }

  public static <K> MutableObjectIntMap<K> createObjectToInt(int expectedSize, float loadFactor) {
    return new MutableObjectIntMap<>(expectedSize);
  }
}
