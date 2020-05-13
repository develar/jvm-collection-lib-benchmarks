package org.jetbrains.benchmark.collection.factory;

import com.koloboke.collect.Equivalence;
import com.koloboke.collect.hash.HashConfig;
import com.koloboke.collect.map.hash.*;

public final class KolobokeFactory {
  public static <K, V> HashObjObjMap<K, V> createObjectToObject(int expectedSize, float loadFactor) {
    return getFactoryWithLoad(loadFactor).newMutableMap(expectedSize);
  }

  public static <K, V> HashObjObjMap<K, V> createReferenceToObject(int expectedSize, float loadFactor) {
    return getFactoryWithLoad(loadFactor).withKeyEquivalence(Equivalence.identity()).newMutableMap(expectedSize);
  }

  public static <K, V> HashObjObjMap<K, V> createObjectToObject(float loadFactor) {
    return getFactoryWithLoad(loadFactor).newMutableMap();
  }

  public static <K, V> HashObjObjMap<K, V> createReferenceToObject(float loadFactor) {
    return getFactoryWithLoad(loadFactor).withKeyEquivalence(Equivalence.identity()).newMutableMap();
  }

  private static HashObjObjMapFactory<Object, Object> getFactoryWithLoad(float loadFactor) {
    return HashObjObjMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor));
  }

  public static HashIntIntMap createIntToInt(float loadFactor) {
    return HashIntIntMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap();
  }

  public static HashIntIntMap createIntToInt(int expectedSize, float loadFactor) {
    return HashIntIntMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap(expectedSize);
  }

  public static <K> HashIntObjMap<K> createIntToObject(float loadFactor) {
    return HashIntObjMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap();
  }

  public static <K> HashIntObjMap<K> createIntToObject(int expectedSize, float loadFactor) {
    return HashIntObjMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap(expectedSize);
  }

  public static <K> HashObjIntMap<K> createObjectToInt(float loadFactor) {
    return HashObjIntMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap();
  }

  public static <K> HashObjIntMap<K> createObjectToInt(int expectedSize, float loadFactor) {
    return HashObjIntMaps.getDefaultFactory().withHashConfig(HashConfig.fromLoads(loadFactor / 2, loadFactor, loadFactor)).newMutableMap(expectedSize);
  }
}
