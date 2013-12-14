package com.javacreed.examples.oop.part3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.javacreed.examples.oop.part3.tax.ChainedTaxCalculator;
import com.javacreed.examples.oop.part3.tax.EcoTaxCalculator;
import com.javacreed.examples.oop.part3.tax.ImportTaxCalculator;
import com.javacreed.examples.oop.part3.tax.SalesTaxCalculator;
import com.javacreed.examples.oop.part3.tax.TaxCalculator;

public class Item {

  public static class Builder {

    private final String name;
    private final BigDecimal originalPrice;
    private final List<TaxCalculator> taxCalculators = new ArrayList<>();

    public Builder(final String name, final BigDecimal originalPrice) {
      this.name = name;
      this.originalPrice = originalPrice;
    }

    public Builder(final String name, final String originalPrice) throws NumberFormatException {
      this(name, new BigDecimal(originalPrice));
    }

    public Builder addAllTaxCalculators() {
      addSalesTaxCalculator();
      addImportTaxCalculator();
      addEcoTaxCalculator();
      return this;
    }

    public Builder addEcoTaxCalculator() {
      return addTaxCalculator(new EcoTaxCalculator());
    }

    public Builder addImportTaxCalculator() {
      return addTaxCalculator(new ImportTaxCalculator());
    }

    public Builder addSalesTaxCalculator() {
      return addTaxCalculator(new SalesTaxCalculator());
    }

    public Builder addTaxCalculator(final TaxCalculator taxCalculator) {
      this.taxCalculators.add(taxCalculator);
      return this;
    }

    public Item build() {
      return new Item(name, originalPrice, new ChainedTaxCalculator(taxCalculators));
    }
  }

  private final String name;
  private final BigDecimal originalPrice;
  private final TaxCalculator taxCalculator;

  public Item(final String name, final BigDecimal originalPrice, final TaxCalculator taxCalculator) {
    this.name = name;
    this.originalPrice = originalPrice;
    this.taxCalculator = taxCalculator;
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
