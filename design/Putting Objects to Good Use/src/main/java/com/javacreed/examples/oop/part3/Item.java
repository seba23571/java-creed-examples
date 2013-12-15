package com.javacreed.examples.oop.part3;

import java.math.BigDecimal;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import com.javacreed.examples.oop.part3.tax.TaxCalculator;

@ThreadSafe
@Immutable
public class Item {

  private final String name;
  private final BigDecimal originalPrice;
  private final TaxCalculator taxCalculator;

  public Item(final String name, final BigDecimal originalPrice, final TaxCalculator taxCalculator) {
    this.name = name;
    this.originalPrice = originalPrice;
    this.taxCalculator = taxCalculator;
  }

  public Item(final String name, final String originalPrice, final TaxCalculator taxCalculator)
      throws NumberFormatException {
    this(name, new BigDecimal(originalPrice), taxCalculator);
  }

  public BigDecimal calculateTax() {
    return taxCalculator.calculateTax(originalPrice);
  }

  public String getName() {
    return name;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public BigDecimal getSellingPrice() {
    return originalPrice.add(calculateTax());
  }

  @Override
  public String toString() {
    return String.format("%s %.2f (TAX %.2f)", name, getSellingPrice(), calculateTax());
  }
}
