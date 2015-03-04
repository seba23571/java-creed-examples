package com.javacreed.examples.multiverse.internal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LoggerUtils {

  private static final LoggerUtils INSTANCE = new LoggerUtils();

  public static Logger getLogger(final Class<?> c) {
    return LoggerUtils.INSTANCE.delegate(c);
  }

  private LoggerUtils() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  private Logger delegate(final Class<?> c) {
    return LoggerFactory.getLogger(c);
  }
}
