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
   * Causes the current thread to stop working for the given {@code time} in millis. Any
   * {@link InterruptedException} are suppressed for convenience but the thread interrupted state is preserved
   * to interrupted.
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
