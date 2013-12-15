package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
@Immutable
public class BasicTaxCalculator extends AbstractTaxCalculator {

  private final BigDecimal tax;

  public BasicTaxCalculator(final BigDecimal tax) {
    this.tax = tax;
  }

  public BasicTaxCalculator(final String tax) {
    this(new BigDecimal(tax));
  }

  @Override
  public BigDecimal calculate(final BigDecimal value) {
    return value.multiply(tax);
  }

}
