package com.javacreed.examples.concurrency.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example1.class);

  public static void main(final String[] args) {
    final Account a = new Account(10);
    a.adjustBy(-5);
    Example1.LOGGER.debug("Account {}", a);

    try {
      a.adjustBy(-10);
    } catch (final IllegalStateException e) {
      Example1.LOGGER.warn("Failed to withdraw money");
    }
    Example1.LOGGER.debug("Account {}", a);
  }

}
