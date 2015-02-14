package com.javacreed.examples.concurrency.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example3 {

  public static final Logger LOGGER = LoggerFactory.getLogger(Example3.class);

  public static void main(final String[] args) throws Exception {
    // Break point 1
    final int limit = 10000000;
    final PojoAccount[] array = new PojoAccount[limit];
    for (int i = 0; i < limit; i++) {
      array[i] = new PojoAccount(i);
    }

    // Break point 2
    Example3.LOGGER.debug("Done");
  }

}
