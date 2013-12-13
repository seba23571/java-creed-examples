package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;

public class EcoTaxCalculator extends AbstractTaxCalculator {

  /**
   * Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no
   * exemptions.
   */
  private final BigDecimal importDuty = new BigDecimal("0.0500");

  @Override
  public BigDecimal doCalculateTax(final BigDecimal value) {
    return value.multiply(importDuty);
  }

}
