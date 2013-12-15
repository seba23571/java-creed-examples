package com.javacreed.examples.oop.part3.examples;

import com.javacreed.examples.oop.part3.Basket;
import com.javacreed.examples.oop.part3.ItemBuilder;

public class Example6 {

  public static void main(final String[] args) {
    final Basket basket = new Basket();
    basket.add(new ItemBuilder("Book", "48.50").build());
    basket.add(new ItemBuilder("Imported Calculator", "12.25").addAllTaxCalculators().build());
    basket.add(new ItemBuilder("Imported Medicine", "8.40").addImportTaxCalculator().build());

    System.out.println(basket);
  }
}
