package com.javacreed.examples.oop.part1;

import java.math.BigDecimal;

public class Item {

  private final BigDecimal price;
  private final BigDecimal taxInPercent;

  public Item(final BigDecimal price, final BigDecimal taxInPercent) {
    this.price = price;
    this.taxInPercent = taxInPercent;
  }

  public BigDecimal getSellingPrice() {
    return price.add(taxInPercent.multiply(price));
  }

  public BigDecimal getTax() {
    return taxInPercent.multiply(price);
  }

  @Override
  public String toString() {
    return String.format("%.2f (%.2f)", getSellingPrice(), getTax());
  }
}
