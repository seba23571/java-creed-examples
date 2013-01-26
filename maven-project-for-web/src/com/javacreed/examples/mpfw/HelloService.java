package com.javacreed.examples.mpfw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

  public String greet() {
    HelloService.LOGGER.debug("Greet service invoked");
    return "Hello from hello service";
  }
}
