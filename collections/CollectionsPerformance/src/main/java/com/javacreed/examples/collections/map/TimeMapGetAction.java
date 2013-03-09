package com.javacreed.examples.collections.map;

import java.util.Map;

public class TimeMapGetAction implements TimeMapAction {

  private final String keyPattern = "Key %d";
  private final String valuePattern = "Value %d";

  @Override
  public String getName() {
    return "get()";
  }

  @Override
  public long timeAction(final Map<String, String> map, final int limit) {
    for (int i = 0; i < limit; i++) {
      map.put(String.format(keyPattern, i), String.format(valuePattern, i));
    }

    final long start = System.nanoTime();
    for (int i = 0, size = map.size(); i < limit; i++) {
      map.get(String.format(keyPattern, i % size));
    }
    return System.nanoTime() - start;
  }

}
