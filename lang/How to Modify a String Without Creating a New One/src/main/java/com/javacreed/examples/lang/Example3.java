/*
 * #%L
 * How to Modify a String Without Creating a New One
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.lang;

import java.lang.reflect.Field;
import java.security.Permission;
import java.util.Arrays;

public class Example3 {
  public static void main(final String[] args) throws Exception {
    System.setSecurityManager(new SecurityManager() {
      @Override
      public void checkPermission(final Permission perm) {
        switch (perm.getName()) {
        // Allows getDeclaredField()
        case "accessDeclaredMembers":
          // Allows setAccessible()
        case "suppressAccessChecks":
          // Allows formatting and printing
        case "user.language.format":
        case "user.script.format":
        case "user.country.format":
        case "user.variant.format":
        case "java.locale.providers":
          // Ignore/Allow
          break;
        default:
            System.out.println(perm.getName());
          super.checkPermission(perm);
        }
      }
    });

    final Class<String> type = String.class;
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
