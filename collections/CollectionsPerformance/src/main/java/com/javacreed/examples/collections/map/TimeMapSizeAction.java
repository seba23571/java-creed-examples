package com.javacreed.examples.collections.map;

import java.util.Map;

public class TimeMapSizeAction implements TimeMapAction {

  private final String keyPattern = "Element %d";
  private final String valuePattern = "Element %d";

  @Override
  public String getName() {
    return "size()";
  }

  @Override
  public long timeAction(final Map<String, String> map, final int limit) {
    for (int i = 0; i < limit; i++) {
      map.put(String.format(keyPattern, i), String.format(valuePattern, i));
    }

    final long start = System.nanoTime();
    for (int i = 0; i < limit; i++) {
      map.size();
    }
    return System.nanoTime() - start;
  }

}
