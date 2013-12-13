package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;

public class NoTaxCalculator implements TaxCalculator {

  @Override
  public BigDecimal calculateTax(final BigDecimal value) {
    return BigDecimal.ZERO;
  }

}
