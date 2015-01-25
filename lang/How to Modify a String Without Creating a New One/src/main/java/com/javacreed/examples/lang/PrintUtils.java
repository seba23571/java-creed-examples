package com.javacreed.examples.lang;

public class PrintUtils {

  public static void print(final String s) {
    System.out.printf("(Object: %d) >%s<%n", System.identityHashCode(s), s);
  }

  private PrintUtils() {}
}
