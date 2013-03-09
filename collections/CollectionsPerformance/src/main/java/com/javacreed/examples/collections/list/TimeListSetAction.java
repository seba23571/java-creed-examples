package com.javacreed.examples.collections.list;

import java.util.List;

public class TimeListSetAction implements TimeListAction {

  private final String pattern = "Element %d";

  @Override
  public String getName() {
    return "set()";
  }

  @Override
  public long timeAction(final List<String> list, final int limit) {
    for (int i = 0; i < limit; i++) {
      list.add("");
    }

    final long start = System.nanoTime();
    for (int i = 0, size = list.size(); i < limit; i++) {
      list.set(i % size, String.format(pattern, i));
    }
    return System.nanoTime() - start;
  }

}
