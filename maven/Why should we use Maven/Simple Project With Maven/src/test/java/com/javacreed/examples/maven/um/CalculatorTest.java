package com.javacreed.examples.maven.um;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
  @Test
  public void testAdd() {
    final Calculator calc = new Calculator();
    Assert.assertEquals(20, calc.add(12, 5, 3), 0.001);
  }

  @Test
  public void testParse() {
    final Calculator calc = new Calculator();
    final double[] numbers = calc.parseNumbers("12 5 3");
    Assert.assertArrayEquals(new double[] { 12, 5, 3 }, numbers, 0.001);
  }
}
