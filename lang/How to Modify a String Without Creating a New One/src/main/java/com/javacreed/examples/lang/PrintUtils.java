package com.javacreed.examples.lang;

public class PrintUtils {

  private PrintUtils() {}

  public static void print(String s) {
    System.out.printf("(Object: %d) >%s<%n", System.identityHashCode(s), s);
  }
}
