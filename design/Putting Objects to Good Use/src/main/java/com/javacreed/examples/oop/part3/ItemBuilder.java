package com.javacreed.examples.oop.part3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.NotThreadSafe;

import com.javacreed.examples.oop.part3.tax.ChainedTaxCalculator;
import com.javacreed.examples.oop.part3.tax.EcoTaxCalculator;
import com.javacreed.examples.oop.part3.tax.ImportTaxCalculator;
import com.javacreed.examples.oop.part3.tax.SalesTaxCalculator;
import com.javacreed.examples.oop.part3.tax.TaxCalculator;

@NotThreadSafe
public class ItemBuilder {

  private final String name;
  private final BigDecimal originalPrice;
  private final List<TaxCalculator> taxCalculators = new ArrayList<>();

  public ItemBuilder(final String name, final BigDecimal originalPrice) {
    this.name = name;
    this.originalPrice = originalPrice;
  }

  public ItemBuilder(final String name, final String originalPrice) throws NumberFormatException {
    this(name, new BigDecimal(originalPrice));
  }

  public ItemBuilder addAllTaxCalculators() {
    addSalesTaxCalculator();
    addImportTaxCalculator();
    addEcoTaxCalculator();
    return this;
  }

  public ItemBuilder addEcoTaxCalculator() {
    return addTaxCalculator(new EcoTaxCalculator());
  }

  public ItemBuilder addImportTaxCalculator() {
    return addTaxCalculator(new ImportTaxCalculator());
  }

  public ItemBuilder addSalesTaxCalculator() {
    return addTaxCalculator(new SalesTaxCalculator());
  }

  public ItemBuilder addTaxCalculator(final TaxCalculator taxCalculator) {
    this.taxCalculators.add(taxCalculator);
    return this;
  }

  public Item build() {
    return new Item(name, originalPrice, new ChainedTaxCalculator(taxCalculators));
  }
}