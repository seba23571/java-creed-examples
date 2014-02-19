package com.javacreed.examples.collections.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Example1 {

  public static void main(final String[] args) {
    final List<String> modifiable = new ArrayList<>();
    modifiable.add("Java");
    modifiable.add("is");

    final List<String> unmodifiable = Collections.unmodifiableList(modifiable);
    System.out.println("Before modification: " + unmodifiable);

    modifiable.add("the");
    modifiable.add("best");

    System.out.println("After modification: " + unmodifiable);
  }

}
