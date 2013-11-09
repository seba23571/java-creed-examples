package com.javacreed.examples.maven.wum;

import org.apache.commons.lang3.math.NumberUtils;

public class Calculator {

  public double add(final double... numbers) {
    double total = 0;
    for (final double number : numbers) {
      total += number;
    }
    return total;
  }

  public double[] parseNumbers(final String text) {
    final String[] parts = text.split(" ");
    final double[] numbers = new double[parts.length];
    for (int i = 0; i < parts.length; i++) {
      if (NumberUtils.isNumber(parts[i])) {
        numbers[i] = Double.parseDouble(parts[i]);
      }
    }

    return numbers;
  }
}
