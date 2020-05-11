package org.jetbrains.benchmark.collection.factory;

import com.koloboke.collect.Equivalence;
import com.koloboke.collect.map.hash.HashObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;

public final class KolobokeFactory {
  public static <K, V> HashObjObjMap<K, V> createObjectToObject(int expectedSize) {
    return HashObjObjMaps.getDefaultFactory().newMutableMap(expectedSize);
  }

  public static <K, V> HashObjObjMap<K, V> createReferenceToObject(int expectedSize) {
    return HashObjObjMaps.getDefaultFactory().withKeyEquivalence(Equivalence.identity()).newMutableMap(expectedSize);
  }

  public static <K, V> HashObjObjMap<K, V> createObjectToObject() {
    return HashObjObjMaps.getDefaultFactory().newMutableMap();
  }

  public static <K, V> HashObjObjMap<K, V> createReferenceToObject() {
    return HashObjObjMaps.getDefaultFactory().withKeyEquivalence(Equivalence.identity()).newMutableMap();
  }
}
