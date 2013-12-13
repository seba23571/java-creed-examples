package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractTaxCalculator implements TaxCalculator {

  @Override
  public BigDecimal calculateTax(final BigDecimal value) {
    /* Rounded up to the nearest 0.05 */
    BigDecimal tax = doCalculateTax(value);
    tax = tax.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
    tax = tax.divide(new BigDecimal("20"), RoundingMode.UP);

    return tax;
  }

  protected abstract BigDecimal doCalculateTax(BigDecimal value);
}
