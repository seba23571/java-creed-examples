package com.javacreed.examples.oop.part3;

public class Main {
  public static void main(final String[] args) {
    final Basket basket = new Basket();
    basket.add(new Item.Builder("Book", "48.50").build());
    basket.add(new Item.Builder("Imported Calculator", "12.25").addAllTaxCalculators().build());
    basket.add(new Item.Builder("Imported Medicine", "8.40").addImportTaxCalculator().build());

    System.out.println(basket);
  }
}
