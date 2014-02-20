/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.deadlock.utils;

/**
 * A stateless class that contains some utilities methods.
 * 
 * @author Albert Attard
 */
public class ThreadUtils {

  /**
   * Prints a formatted message to the command prompt
   * 
   * @param format
   *          the message (format)
   * @param params
   *          the message parameters (optional)
   */
  public static void log(final String format, final Object... params) {
    System.out.printf("%tT [%s] %s%n", System.currentTimeMillis(), Thread.currentThread().getName(),
        String.format(format, params));
  }

  /**
   * Causes the current thread to stop working for the given {@code time} in millis. Any {@link InterruptedException}
   * are suppressed for convenience but the thread interrupted state is preserved to interrupted.
   * 
   * @param time
   *          the sleep time in millis
   */
  public static void silentSleep(final long time) {
    try {
      Thread.sleep(time);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private ThreadUtils() {
  }
}
