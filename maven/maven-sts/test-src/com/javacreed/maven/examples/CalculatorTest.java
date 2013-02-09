package com.javacreed.maven.examples;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

  @Test
  public void test() {
    final Calculator calculator = new CalculatorImpl();
    final double actual = calculator.sum(1, 1);
    Assert.assertEquals(2, actual, 0.0001);
  }
}
