package com.javacreed.examples.oop.part3.examples;

import com.javacreed.examples.oop.part3.Item;
import com.javacreed.examples.oop.part3.tax.BasicTaxCalculator;
import com.javacreed.examples.oop.part3.tax.TaxCalculator;

public class Example4 {

  @SuppressWarnings("unused")
  private static final TaxCalculator SALES_TAX = new BasicTaxCalculator("0.1800");

  private static final TaxCalculator IMPORT_TAX = new BasicTaxCalculator("0.0300");

  @SuppressWarnings("unused")
  private static final TaxCalculator ECO_TAX = new BasicTaxCalculator("0.0500");

  public static void main(final String[] args) {
    final Item medicine = new Item("Imported Medicine", "8.40", Example4.IMPORT_TAX);
    System.out.println(medicine);
  }
}
