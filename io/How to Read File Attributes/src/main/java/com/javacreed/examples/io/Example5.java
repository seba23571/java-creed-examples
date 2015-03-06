package com.javacreed.examples.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Example5 {

  private static class MediaInfo {

    public static MediaInfo parse(final String data) {

      final MediaInfo mediaInfo = new MediaInfo();

      Section section = null;

      for (final String line : data.split("(\\r\\n|\\r|\\n)")) {
        if (line.isEmpty()) {
          section = null;
          continue;
        }

        if (section == null) {
          section = mediaInfo.addSection(Section.parse(line));
          continue;
        }

        section.add(NameValue.parse(line));
      }

      return mediaInfo;
    }

    private final Map<String, Section> sections = new LinkedHashMap<>();

    public Section addSection(final Section section) {
      final String name = section.getName();
      if (sections.containsKey(name)) {
        throw new IllegalArgumentException("Duplicate section name: '" + name + "'");
      }

      sections.put(name, section);
      return section;
    }

    @Override
    public String toString() {
      final StringBuilder formatted = new StringBuilder();
      if (sections.isEmpty()) {
        formatted.append("No information found!!!");
      } else {
        for (final Section section : sections.values()) {
          formatted.append(section);
        }
      }

      return formatted.toString();
    }
  }

  public static class NameValue {
    public static NameValue parse(final String line) {
      final String[] parts = line.split(":", 2);
      return new NameValue(parts[0].trim(), parts[1].trim());
    }

    private final String name;
    private final String value;

    public NameValue(final String name, final String value) {
      this.name = Objects.requireNonNull(name);
      this.value = Objects.requireNonNull(value);
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.format("[%s] = '%s'", name, value);
    }

  }

  private static class Section {
    public static Section parse(final String line) {
      if (line.contains(":")) {
        throw new IllegalArgumentException("Section name should not have ':'");
      }

      return new Section(line.trim());
    }

    private final String name;

    private final Map<String, NameValue> values = new LinkedHashMap<>();

    public Section(final String name) {
      this.name = Objects.requireNonNull(name);
    }

    public void add(final NameValue nameValue) {
      final String name = nameValue.getName();
      if (values.containsKey(name)) {
        throw new IllegalArgumentException("Duplicate name: '" + name + "'");
      }

      values.put(name, nameValue);
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      final StringBuilder formatted = new StringBuilder();
      formatted.append(name).append("\n");
      if (values.isEmpty()) {
        formatted.append("  No values!!!\n");
      } else {
        for (final NameValue nameValue : values.values()) {
          formatted.append("  ").append(nameValue).append("\n");
        }
      }
      return formatted.toString();
    }
  }

  private static String executeMediaInfo(final String mediaPath) throws IOException, InterruptedException {
    final String exePath = "C:\\Users\\Mona Lisa\\Downloads\\MediaInfo_CLI_0.7.72_Windows_x64\\MediaInfo.exe";
    final ProcessBuilder builder = new ProcessBuilder(exePath, mediaPath);
    builder.redirectErrorStream();
    final Process process = builder.start();

    final StringBuilder buffer = new StringBuilder();
    try (Reader reader = new InputStreamReader(process.getInputStream())) {
      for (int i; (i = reader.read()) != -1;) {
        buffer.append((char) i);
      }
    }

    final int status = process.waitFor();
    if (status == 0) {
      return buffer.toString();
    }

    throw new IOException("Unexpected exit status " + status);
  }

  public static void main(final String[] args) throws Exception {

    final String mediaPath = "\\\\192.168.75.25\\Data\\sd\\videos\\Argentina\\2014\\Primera_A\\Arsenal_de_Sarandí_vs._Banfield\\937560_0\\Arsenal_de_Sarandí_vs._Banfield.mp4";

    final String data = Example5.executeMediaInfo(mediaPath);
    final MediaInfo mediaInfo = MediaInfo.parse(data);
    System.out.println(mediaInfo);
    System.out.println("Done");
  }
}
