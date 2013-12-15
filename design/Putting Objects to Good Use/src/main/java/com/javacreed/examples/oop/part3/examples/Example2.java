package com.javacreed.examples.oop.part3.examples;

import com.javacreed.examples.oop.part3.Item;
import com.javacreed.examples.oop.part3.tax.ChainedTaxCalculator;
import com.javacreed.examples.oop.part3.tax.EcoTaxCalculator;
import com.javacreed.examples.oop.part3.tax.ImportTaxCalculator;
import com.javacreed.examples.oop.part3.tax.SalesTaxCalculator;

public class Example2 {
  public static void main(final String[] args) {
    final ChainedTaxCalculator taxCalculator = new ChainedTaxCalculator(new SalesTaxCalculator(),
        new ImportTaxCalculator(), new EcoTaxCalculator());
    final Item calculator = new Item("Imported Calculator", "12.25", taxCalculator);
    System.out.println(calculator);
  }
}
