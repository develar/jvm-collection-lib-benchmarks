package org.jetbrains.benchmark.collection;

import java.util.Random;

public final class Util {
  private Util() {
  }

  public static ArbitraryPojo[] loadObjectArray(String mapSize) {
    int[] ints = loadIntArray(mapSize);
    ArbitraryPojo[] result = new ArbitraryPojo[ints.length];
    for (int i = 0, n = ints.length; i < n; i++) {
      int anInt = ints[i];
      result[i] = new ArbitraryPojo(anInt, anInt * 31);
    }
    return result;
  }

  public static int[] loadIntArray(String mapSize) {
    return KeyGenerator.getInts(parseSize(mapSize));
  }

  public static int parseSize(String value) {
    for (ConversionUnit unit : ConversionUnit.conversionMatrix) {
      int endIndex = value.indexOf(unit.suffix);
      if (endIndex > -1) {
        return (int) (Float.parseFloat(value.substring(0, endIndex)) * unit.value);
      }
    }
    return Integer.parseInt(value);
  }

  public static String formatSize(int value) {
    if (value < 1000) {
      return Integer.toString(value);
    }

    for (int i = ConversionUnit.conversionMatrix.length - 1; i >= 0; i--) {
      ConversionUnit unit = ConversionUnit.conversionMatrix[i];
      if (unit.value <= value) {
        int truncated = value / (unit.value / 10);
        @SuppressWarnings("IntegerDivisionInFloatingPointContext")
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + unit.suffix : (truncated / 10) + unit.suffix;
      }
    }
    return Integer.toString(value);
  }
}

final class KeyGenerator {
  private static int[] values;

  public static int[] getInts(int mapSize) {
    int[] values = KeyGenerator.values;
    if (values != null && mapSize == values.length) {
      return values;
    }

    KeyGenerator.values = null;
    values = new int[mapSize];
    // constant seed to produce same numbers
    Random random = new Random(1234);
    for (int i = 0; i < mapSize; i++) {
      values[i] = random.nextInt();
    }

    KeyGenerator.values = values;
    return values;
  }
}

final class ConversionUnit {
  static final ConversionUnit[] conversionMatrix = {
    new ConversionUnit("K", 1_000),
    new ConversionUnit("M", 1_000_000),
    new ConversionUnit("B", 1_000_000_000)
  };

  String suffix;
  int value;

  private ConversionUnit(String suffix, int value) {
    this.suffix = suffix;
    this.value = value;
  }
}