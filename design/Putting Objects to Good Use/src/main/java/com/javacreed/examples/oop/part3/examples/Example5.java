package com.javacreed.examples.oop.part3.examples;

import com.javacreed.examples.oop.part3.Item;
import com.javacreed.examples.oop.part3.ItemBuilder;

public class Example5 {

  public static void main(final String[] args) {
    final Item calculator = new ItemBuilder("Imported Calculator", "12.25")//@formatter:off
                                  .addSalesTaxCalculator()
                                  .addImportTaxCalculator()
                                  .addEcoTaxCalculator()
                                  .build();//@formatter:on
    System.out.println(calculator);
  }
}
