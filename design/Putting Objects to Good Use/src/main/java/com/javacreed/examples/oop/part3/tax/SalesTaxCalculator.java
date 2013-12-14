package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;

public class SalesTaxCalculator extends AbstractTaxCalculator {

  private final BigDecimal tax = new BigDecimal("0.1800");

  @Override
  public BigDecimal doCalculateTax(final BigDecimal value) {
    return value.multiply(tax);
  }

}
