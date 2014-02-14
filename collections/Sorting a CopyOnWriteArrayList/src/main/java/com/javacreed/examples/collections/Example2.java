package com.javacreed.examples.collections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Example2 {

  public static void main(final String[] args) {
    final List<String> list = new CopyOnWriteArrayList<>();
    CollectionsUtil.addInOrder(list, "3");
    CollectionsUtil.addInOrder(list, "2");
    CollectionsUtil.addInOrder(list, "1");
    CollectionsUtil.addInOrder(list, "3");

    System.out.println(list);
  }
}
