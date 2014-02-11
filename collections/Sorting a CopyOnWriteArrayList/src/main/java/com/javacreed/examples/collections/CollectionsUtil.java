package com.javacreed.examples.collections;

import java.util.Collections;
import java.util.List;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class CollectionsUtil {

  public static <T extends Comparable<T>> int addInOrder(final List<T> list, final T item) {
    // The index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
    final int index = Collections.binarySearch(list, item);
    if (index < 0) {
      final int insertAt = -(index + 1);
      list.add(insertAt, item);
      return insertAt;
    }

    return -1;
  }

  private CollectionsUtil() {
  }
}
