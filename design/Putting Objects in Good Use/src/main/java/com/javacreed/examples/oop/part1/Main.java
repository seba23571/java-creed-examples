package com.javacreed.examples.oop.part1;

public class Main {

  public static void main(final String[] args) {
    final Basket basket = new Basket();
    basket.add(new Item("Book", "48.50", "0"));
    basket.add(new Item("Imported Calculator", "12.25", "0.26"));
    basket.add(new Item("Imported Medicine", "8.40", "0.03"));

    System.out.println(basket);
  }
}
