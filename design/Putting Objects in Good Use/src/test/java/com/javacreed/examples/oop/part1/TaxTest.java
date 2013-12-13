package com.javacreed.examples.oop.part1;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class TaxTest {

  @Test
  public void test() {
    final Basket basket = new Basket();
    basket.add(new Item("Book", "48.50", "0"));
    basket.add(new Item("Imported Calculator", "12.25", "0.26"));
    basket.add(new Item("Imported Medicine", "8.40", "0.03"));

    Assert.assertEquals(3, basket.size());
    Assert.assertEquals(new BigDecimal("3.60"), basket.getTotalTax());
    Assert.assertEquals(new BigDecimal("72.75"), basket.getTotalPrice());
  }
}
