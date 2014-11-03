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
