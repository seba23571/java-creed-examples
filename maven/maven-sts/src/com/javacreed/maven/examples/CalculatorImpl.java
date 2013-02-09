package com.javacreed.maven.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorImpl implements Calculator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorImpl.class);

  @Override
  public double sum(double a, double b) {
    double result = a + b;
    LOGGER.debug("The sum of {} and {} is {}", new Object[] { a, b, result });
    return result;
  }

}
