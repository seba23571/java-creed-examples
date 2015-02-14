package com.javacreed.examples.concurrency.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example2.class);

  public static void main(final String[] args) {
    final Account a = new Account(10);
    final Account b = new Account(10);

    a.transferBetween(b, 5);
    Example2.LOGGER.debug("Account (a) {}", a);
    Example2.LOGGER.debug("Account (b) {}", b);

    try {
      a.transferBetween(b, 20);
    } catch (final IllegalStateException e) {
      Example2.LOGGER.warn("Failed to transfer money");
    }

    Example2.LOGGER.debug("Account (a) {}", a);
    Example2.LOGGER.debug("Account (b) {}", b);
  }

}
