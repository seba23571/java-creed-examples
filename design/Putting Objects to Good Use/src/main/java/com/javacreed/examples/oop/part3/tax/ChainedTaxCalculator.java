package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
@Immutable
public class ChainedTaxCalculator implements TaxCalculator {

  private final List<TaxCalculator> taxCalculators = new ArrayList<>();

  public ChainedTaxCalculator(final Collection<TaxCalculator> taxCalculators) {
    this.taxCalculators.addAll(taxCalculators);
  }

  public ChainedTaxCalculator(final TaxCalculator... taxCalculators) {
    this.taxCalculators.addAll(Arrays.asList(taxCalculators));
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
