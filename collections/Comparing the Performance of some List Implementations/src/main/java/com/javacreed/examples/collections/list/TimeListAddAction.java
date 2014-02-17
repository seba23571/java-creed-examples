package com.javacreed.examples.collections.list;

import java.util.List;

public class TimeListAddAction implements TimeListAction {

  private final String pattern = "Element %d";

  @Override
  public String getName() {
    return "add()";
  }

  @Override
  public long timeAction(final List<String> list, final int limit) {
    final long start = System.nanoTime();
    for (int i = 0; i < limit; i++) {
      list.add(String.format(pattern, i));
    }
    return System.nanoTime() - start;
  }

}
