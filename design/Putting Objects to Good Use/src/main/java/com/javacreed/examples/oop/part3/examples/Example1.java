package com.javacreed.examples.oop.part3.examples;

import com.javacreed.examples.oop.part3.Item;
import com.javacreed.examples.oop.part3.tax.NoTaxCalculator;

public class Example1 {
  public static void main(final String[] args) {
    final Item book = new Item("Effective Java", "32.44", new NoTaxCalculator());
    System.out.println(book);
  }
}
