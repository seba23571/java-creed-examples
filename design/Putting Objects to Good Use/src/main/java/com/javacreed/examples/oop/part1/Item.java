package com.javacreed.examples.oop.part1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

  private final String name;
  private final BigDecimal price;
  private final BigDecimal tax;

  public Item(final String name, final BigDecimal price, final BigDecimal tax) {
    this.name = name;
    this.price = price;
    this.tax = tax;
  }

  public Item(final String name, final String price, final String tax) throws NumberFormatException {
    this(name, new BigDecimal(price), new BigDecimal(tax));
  }

  public BigDecimal calculateTax() {
    BigDecimal taxValue = tax.multiply(price);
    taxValue = taxValue.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
    taxValue = taxValue.divide(new BigDecimal("20"), RoundingMode.UP);

    return taxValue;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getSellingPrice() {
    return price.add(calculateTax());
  }

  @Override
  public String toString() {
    return String.format("%s %.2f (%.2f)", name, getSellingPrice(), calculateTax());
  }
}
