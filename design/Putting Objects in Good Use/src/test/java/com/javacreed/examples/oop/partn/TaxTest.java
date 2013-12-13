package com.javacreed.examples.oop.partn;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.oop.part3.Basket;
import com.javacreed.examples.oop.part3.Item;

public class TaxTest {

  @Test
  public void test() {
    final Basket basket = new Basket();
    basket.add(new Item.Builder("Book", "48.50").build());
    basket.add(new Item.Builder("Imported Calculator", "12.25").addAllTaxCalculators().build());
    basket.add(new Item.Builder("Imported Medicine", "8.40").addImportTaxCalculator().build());

    Assert.assertEquals(3, basket.size());
    Assert.assertEquals(new BigDecimal("3.60"), basket.getTotalTax());
    Assert.assertEquals(new BigDecimal("72.75"), basket.getTotalPrice());
  }
}
