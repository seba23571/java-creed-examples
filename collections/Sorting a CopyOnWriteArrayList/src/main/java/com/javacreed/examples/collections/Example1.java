package com.javacreed.examples.collections;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Example1 {

  public static void main(final String[] args) {
    final List<String> list = new CopyOnWriteArrayList<>();
    list.add("3");
    list.add("2");
    list.add("1");

    Collections.sort(list);
  }
}
