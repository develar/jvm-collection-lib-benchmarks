package tests;

import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class ResultToCsv {
  private static final Int2IntOpenHashMap indexToSize = new Int2IntOpenHashMap(5);

  static {
    indexToSize.put(1, 10_000);
    indexToSize.put(2, 100_000);
    indexToSize.put(3, 1_000_000);
    indexToSize.put(4, 10_000_000);
    indexToSize.put(5, 100_000_000);
  }

  static class Entry {
    String operation;
    final int value;
    final int size;

    Entry(int value, String operation, int size) {
      this.value = value;
      this.operation = operation;
      this.size = size;
    }
  }

  public static void main(String[] args) throws IOException {
    LinkedHashMap<String, LinkedHashMap<String, List<Entry>>> subTypeToEntries = new LinkedHashMap<>();
    for (String type : Arrays.asList("get", "put", "remove")) {
      LinkedHashMap<String, List<String>> result = new LinkedHashMap<>();

      List<String> lines = Files.readAllLines(Paths.get("results", type + ".csv"));
      for (int i = 1, linesSize = lines.size(); i < linesSize; i++) {
        String line = lines.get(i);
        String[] entries = line.split(",");
        String name = entries[0];
        //noinspection SpellCheckingInspection
        name = name.replace("tests.maptests.", "");

        String rawSubType = name.substring(0, name.indexOf('.'));
        if (rawSubType.equals("article_examples")) {
          continue;
        }

        String subType = rawSubType;
        if (subType.equals("primitive")) {
          subType = "IntToInt";
        }
        else if (subType.equals("prim_object")) {
          subType = "IntToObject";
        }
        else if (subType.equals("object_prim")) {
          subType = "ObjectToInt";
        }
        else if (subType.equals("object")) {
          subType = "ObjectToObject";
        }
        else if (subType.equals("identity_object")) {
          subType = "ReferenceToObject";
        }

        String libName;
        if (name.contains("FastUtil")) {
          libName = "Fastutil";
        }
        else if (name.contains("Koloboke")) {
          libName = "Koloboke";
          if (name.contains("KolobokeNotNullKeyObjTest")) {
            libName = "Koloboke not null key";
          }
          else if (name.contains("KolobokeHashCodeMixingObjTest")) {
            libName = "Koloboke mixing keys' hashes";
          }
        }
        else if (name.contains("Hppc")) {
          libName = "HPPC";
        }
        else if (name.contains("Gs")) {
          libName = "EC";
        }
        else if (name.contains("TroveJb")) {
          libName = "Trove JB";
        }
        else if (name.contains("Jdk") || name.contains("JDK")) {
          libName = "JDK";
        }
        else if (name.contains("Trove")) {
          libName = "Trove";
        }
        else {
          throw new IllegalStateException("Unknown lib name: " + name);
        }

        entries[0] = libName;

        result.computeIfAbsent(subType, __ -> {
          List<String> l = new ArrayList<>();
          // google drive doesn't like missed column
          l.add("\"\"" + lines.get(0));
          return l;
        }).add(String.join(",", entries));

        LinkedHashMap<String, List<Entry>> libNameToEntries = subTypeToEntries.computeIfAbsent(subType, __ -> new LinkedHashMap<>());
        for (int i1 = 1; i1 < entries.length; i1++) {
          String value = entries[i1];
          libNameToEntries.computeIfAbsent(libName, __ -> new ArrayList<>()).add(new Entry(Integer.parseInt(value.replaceAll("^\"|\"$", "")), type, indexToSize.get(i1)));
        }
      }

      //System.out.println("=".repeat(64));
      //write(type, result);
    }

    writeJson(subTypeToEntries);
  }

  private static void writeJson(LinkedHashMap<String, LinkedHashMap<String, List<Entry>>> subTypeToEntries) throws IOException {
    Path outFile = Paths.get("site", "data.json");
    Files.createDirectories(outFile.getParent());

    try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(outFile))) {
      writer.setIndent("  ");
      writer.beginObject();
      for (Map.Entry<String, LinkedHashMap<String, List<Entry>>> subTypeToLines : subTypeToEntries.entrySet()) {
        writer.name(subTypeToLines.getKey());
        writer.beginArray();

        List<String> sortedLibNames = new ArrayList<>(subTypeToLines.getValue().keySet());
        sortedLibNames.sort(null);
        for (String libName : sortedLibNames) {
          writer.beginObject();
          writer.name("name").value(libName);

          writer.name("data");
          writer.beginArray();
          List<Entry> entries = subTypeToLines.getValue().get(libName);

          Int2ObjectLinkedOpenHashMap<List<Entry>> map = new Int2ObjectLinkedOpenHashMap<>();
          for (Entry entry : entries) {
            map.computeIfAbsent(entry.size, __ -> new ArrayList<>()).add(entry);
          }

          for (ObjectBidirectionalIterator<Int2ObjectMap.Entry<List<Entry>>> iterator = map.int2ObjectEntrySet().fastIterator(); iterator.hasNext(); ) {
            Int2ObjectMap.Entry<List<Entry>> sizeAndList = iterator.next();
            writer.beginObject();
            writer.name("size").value(sizeAndList.getIntKey());
            for (Entry entry : sizeAndList.getValue()) {
              writer.name(entry.operation).value(entry.value);
            }
            writer.endObject();
          }

          writer.endArray();
          writer.endObject();
        }
        writer.endArray();
      }
      writer.endObject();
    }
  }

  //private static void write(String type, LinkedHashMap<String, List<String>> result) throws IOException {
  //  for (Map.Entry<String, List<String>> subTypeToLines : result.entrySet()) {
  //    System.out.println("-".repeat(64));
  //    String subType = subTypeToLines.getKey();
  //    String name = type + " " + subType;
  //    System.out.println(name);
  //    List<String> subLines = subTypeToLines.getValue();
  //    System.out.println(String.join("\n", subLines));
  //    Files.write(Paths.get("results", name + ".csv"), subLines, StandardCharsets.UTF_8);
  //  }
  //}
}
