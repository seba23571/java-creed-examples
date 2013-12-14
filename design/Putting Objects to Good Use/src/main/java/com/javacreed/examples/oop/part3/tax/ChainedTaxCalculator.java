package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChainedTaxCalculator implements TaxCalculator {

  private final List<TaxCalculator> taxCalculators = new ArrayList<>();

  public ChainedTaxCalculator(final Collection<TaxCalculator> taxCalculators) {
    this.taxCalculators.addAll(taxCalculators);
  }

  @Override
  public BigDecimal calculateTax(final BigDecimal value) {
    BigDecimal tax = BigDecimal.ZERO;
    for (final TaxCalculator taxCalculator : taxCalculators) {
      tax = tax.add(taxCalculator.calculateTax(value));
    }

    return tax;
  }

}
