package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractTaxCalculator implements TaxCalculator {

  protected abstract BigDecimal calculate(BigDecimal value);

  @Override
  public BigDecimal calculateTax(final BigDecimal value) {
    final BigDecimal tax = calculate(value);
    final BigDecimal rounded = round(tax);
    return rounded;
  }

  protected BigDecimal round(BigDecimal value) {
    /* Rounded up to the nearest 0.05 */
    value = value.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
    value = value.divide(new BigDecimal("20"), RoundingMode.UP);
    return value;
  }
}
