package com.javacreed.examples.oop.part1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

  private final String name;
  private final BigDecimal price;
  private final BigDecimal taxInPercent;

  public Item(final String name, final BigDecimal price, final BigDecimal taxInPercent) {
    this.name = name;
    this.price = price;
    this.taxInPercent = taxInPercent;
  }

  public Item(final String name, final String price, final String taxInPercent) throws NumberFormatException {
    this(name, new BigDecimal(price), new BigDecimal(taxInPercent));
  }

  public BigDecimal calculateTax() {
    BigDecimal tax = taxInPercent.multiply(price);
    tax = tax.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
    tax = tax.divide(new BigDecimal("20"), RoundingMode.UP);

    return tax;
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
