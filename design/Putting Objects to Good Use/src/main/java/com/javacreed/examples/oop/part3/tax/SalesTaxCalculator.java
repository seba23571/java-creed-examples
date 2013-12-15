package com.javacreed.examples.oop.part3.tax;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
@Immutable
public class SalesTaxCalculator extends BasicTaxCalculator {

  public SalesTaxCalculator() {
    super("0.1800");
  }
}
