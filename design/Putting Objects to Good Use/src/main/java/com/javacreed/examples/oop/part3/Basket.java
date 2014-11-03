/*
 * #%L
 * Putting Objects to Good Use
 * $Id:$
 * $HeadURL$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.oop.part3;

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
