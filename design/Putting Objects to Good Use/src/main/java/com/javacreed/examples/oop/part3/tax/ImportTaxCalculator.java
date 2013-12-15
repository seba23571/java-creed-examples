package com.javacreed.examples.oop.part3.tax;

/**
 * Import duty is an additional sales tax applicable on all imported goods at a rate of 3%, with no
 * exemptions.
 */
public class ImportTaxCalculator extends BasicTaxCalculator {

  public ImportTaxCalculator() {
    super("0.0300");
  }
}
