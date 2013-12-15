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
  private final BigDecimal[] taxes;

  public Item(final String name, final BigDecimal price, final BigDecimal... taxes) {
    this.name = name;
    this.price = price;
    this.taxes = taxes;
  }

  public Item(final String name, final String price, final String... taxes) throws NumberFormatException {
    this(name, new BigDecimal(price), Item.parseAll(taxes));
  }

  public BigDecimal calculateTax() {
    BigDecimal totalTax = BigDecimal.ZERO;
    for (final BigDecimal tax : this.taxes) {
      BigDecimal taxValue = tax.multiply(price);
      taxValue = taxValue.multiply(new BigDecimal("20")).setScale(0, RoundingMode.UP).setScale(2);
      taxValue = taxValue.divide(new BigDecimal("20"), RoundingMode.UP);

      totalTax = totalTax.add(taxValue);
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
