package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;

public interface TaxCalculator {

  BigDecimal calculateTax(BigDecimal value);
}
