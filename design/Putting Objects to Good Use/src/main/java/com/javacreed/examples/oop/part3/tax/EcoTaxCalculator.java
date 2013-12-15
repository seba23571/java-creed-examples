package com.javacreed.examples.oop.part3.tax;

/**
 * Eco Tax is an additional sales tax applicable on all electronic goods at a rate of 5%, with no exemptions.
 */
public class EcoTaxCalculator extends BasicTaxCalculator {

  public EcoTaxCalculator() {
    super("0.0500");
  }
}
