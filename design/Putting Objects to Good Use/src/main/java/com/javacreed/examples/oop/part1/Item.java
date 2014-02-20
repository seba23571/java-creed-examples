/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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
