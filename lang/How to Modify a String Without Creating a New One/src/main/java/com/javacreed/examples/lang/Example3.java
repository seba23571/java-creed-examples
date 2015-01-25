package com.javacreed.examples.lang;

import java.lang.reflect.Field;
import java.security.Permission;
import java.util.Arrays;

public class Example3 {
  public static void main(final String[] args) throws Exception {
    // java -Djava.security.manager
    System.setSecurityManager(new SecurityManager() {
      @Override
      public void checkPermission(final Permission perm) {}
    });

    final Class<String> type = String.class;
    // The following line fails with an AccessControlException exception
    final Field valueField = type.getDeclaredField("value");
    valueField.setAccessible(true);

    final String s = "Immutable String";
    PrintUtils.print(s);
    final char[] value = (char[]) valueField.get(s);
    value[0] = 'i';
    value[10] = 's';
    PrintUtils.print(s);

    Arrays.fill(value, (char) 0);
    System.arraycopy("Mutable String".toCharArray(), 0, value, 0, 14);
    PrintUtils.print(s);

    valueField.set(s, "Mutable String".toCharArray());
    PrintUtils.print(s);
  }
}
