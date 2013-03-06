package com.javacreed.examples.collections.list;

import java.util.Iterator;
import java.util.List;

public class TimeListIteratorAction implements TimeListAction {

  private final String pattern = "Element %d";

  @Override
  public String getName() {
    return "iterate()";
  }

  @Override
  public long timeAction(final List<String> list, final int limit) {
    for (int i = 0; i < limit; i++) {
      list.add(String.format(pattern, i));
    }

    final long start = System.nanoTime();
    for (final Iterator<String> i = list.iterator(); i.hasNext();) {
      i.next();
    }
    return System.nanoTime() - start;
  }

}
