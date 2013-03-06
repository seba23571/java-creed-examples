package com.javacreed.examples.collections.list;

import java.util.List;

public class TimeListSizeAction implements TimeListAction {

  private final String pattern = "Element %d";

  @Override
  public String getName() {
    return "size()";
  }

  @Override
  public long timeAction(final List<String> list, final int limit) {
    for (int i = 0; i < limit; i++) {
      list.add(String.format(pattern, i));
    }

    final long start = System.nanoTime();
    for (int i = 0; i < limit; i++) {
      list.size();
    }
    return System.nanoTime() - start;
  }

}
