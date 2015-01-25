package com.javacreed.examples.lang;

import java.lang.reflect.Field;

public class Example2 {
  public static void main(final String[] args) throws Exception {
    // java -Djava.security.manager
    System.setSecurityManager(new SecurityManager());

    final Class<String> type = String.class;
    // The following line fails with an AccessControlException exception
    final Field valueField = type.getDeclaredField("value");
    valueField.setAccessible(true);
  }
}
