package com.javacreed.examples.oop.part1;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Basket {

  private final List<Item> items = new ArrayList<>();

  public void add(final Item item) {
    items.add(item);
  }

  public BigDecimal getTotalPrice() {
    BigDecimal total = BigDecimal.ZERO;
    for (final Item item : items) {
      total = total.add(item.getSellingPrice());
    }

    return total;
  }

  public BigDecimal getTotalTax() {
    BigDecimal total = BigDecimal.ZERO;
    for (final Item item : items) {
      total = total.add(item.calculateTax());
    }

    return total;
  }

  public String printReceipt() {
    final StringBuilder formatted = new StringBuilder();

    final NumberFormat numberFormat = new DecimalFormat("#,##0.00");

    formatted.append("Items\n");
    for (final Item item : items) {
      formatted.append(" ");
      formatted.append(String.format("%-24s", item.getName()));
      formatted.append(String.format("%6s", numberFormat.format(item.getSellingPrice())));
      formatted.append("\n");
    }

    formatted.append("\nSummary\n");
    formatted.append(" Items").append(String.format("%25s%n", size()));
    formatted.append(" Tax").append(String.format("%27s%n", numberFormat.format(getTotalTax())));
    formatted.append("--------------------------------\n");
    formatted.append(" Total").append(String.format("%25s%n", numberFormat.format(getTotalPrice())));
    formatted.append("--------------------------------");

    return formatted.toString();
  }

  public int size() {
    return items.size();
  }

  @Override
  public String toString() {
    return printReceipt();
  }
}
