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
package com.javacreed.examples.oop.part3.tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
@Immutable
public class ChainedTaxCalculator implements TaxCalculator {

  private final List<TaxCalculator> taxCalculators = new ArrayList<>();

  public ChainedTaxCalculator(final Collection<TaxCalculator> taxCalculators) {
    this.taxCalculators.addAll(taxCalculators);
  }

  public ChainedTaxCalculator(final TaxCalculator... taxCalculators) {
    this.taxCalculators.addAll(Arrays.asList(taxCalculators));
  }

  @Override
  public BigDecimal calculateTax(final BigDecimal value) {
    BigDecimal tax = BigDecimal.ZERO;
    for (final TaxCalculator taxCalculator : taxCalculators) {
      tax = tax.add(taxCalculator.calculateTax(value));
    }

    return tax;
  }

}
