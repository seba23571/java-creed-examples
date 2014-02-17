package com.javacreed.examples.collections.map;

import java.util.Map;

public class TimeMapPutAction implements TimeMapAction {

  private final String keyPattern = "Key %d";
  private final String valuePattern = "Value %d";

  @Override
  public String getName() {
    return "put()";
  }

  @Override
  public long timeAction(final Map<String, String> map, final int limit) {
    final long start = System.nanoTime();
    for (int i = 0; i < limit; i++) {
      map.put(String.format(keyPattern, i), String.format(valuePattern, i));
    }
    return System.nanoTime() - start;
  }

}
