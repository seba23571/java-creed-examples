package com.javacreed.examples.oop.part2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {

  private static BigDecimal[] parseAll(final String... strings) throws NumberFormatException {
    final BigDecimal[] numbers = new BigDecimal[strings.length];
    for (int i = 0; i < strings.length; i++) {
      numbers[i] = new BigDecimal(strings[i]);
    }
    return numbers;
  }

  private final String name;
  private final BigDecimal price;
  private final BigDecimal[] taxesInPercent;

  public Item(final String name, final BigDecimal price, final BigDecimal... taxesInPercent) {
    this.name = name;
    this.price = price;
    this.taxesInPercent = taxesInPercent;
  }

  public Item(final String name, final String price, final String... taxesInPercent) throws NumberFormatException {
    this(name, new BigDecimal(price), Item.parseAll(taxesInPercent));
  }

  public BigDecimal calculateTax() {
    BigDecimal totalTax = BigDecimal.ZERO;
    for (final BigDecimal taxInPercent : this.taxesInPercent) {
      BigDecimal tax = taxInPercent.multiply(price);
      tax = tax.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
      tax = tax.divide(new BigDecimal("20"), RoundingMode.UP);

      totalTax = totalTax.add(tax);
    }

    return totalTax;
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
