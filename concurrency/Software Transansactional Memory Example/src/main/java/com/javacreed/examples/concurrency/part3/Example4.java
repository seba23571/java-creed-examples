package com.javacreed.examples.concurrency.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example4 {

  public static final Logger LOGGER = LoggerFactory.getLogger(Example4.class);

  public static void main(final String[] args) throws Exception {
    // Break point 1
    final int limit = 10000000;
    final StmAccount[] array = new StmAccount[limit];
    for (int i = 0; i < limit; i++) {
      array[i] = new StmAccount(i);
    }

    // Break point 2
    Example4.LOGGER.debug("Done");
  }

}
