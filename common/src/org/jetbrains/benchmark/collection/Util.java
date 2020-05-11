package org.jetbrains.benchmark.collection;

import com.amazon.ion.IonReader;
import com.amazon.ion.system.IonReaderBuilder;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Util {
  private Util() {
  }

  public static ArbitraryPojo[] loadObjectArray(String mapSize) throws IOException {
    Path inputFile = Paths.get("testData", formatSize(parseSize(mapSize)) + ".ion");
    try (IonReader reader = IonReaderBuilder.standard().build(Files.newInputStream(inputFile))) {
      return new IonObjectMapper().readValue(reader, ArbitraryPojo[].class);
    }
  }

  public static int[] loadIntArray(String mapSize) throws IOException {
    Path inputFile = Paths.get("testData", "int-" + formatSize(parseSize(mapSize)) + ".ion");
    try (IonReader reader = IonReaderBuilder.standard().build(Files.newInputStream(inputFile))) {
      return new IonObjectMapper().readValue(reader, int[].class);
    }
  }

  private static int parseSize(String value) {
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