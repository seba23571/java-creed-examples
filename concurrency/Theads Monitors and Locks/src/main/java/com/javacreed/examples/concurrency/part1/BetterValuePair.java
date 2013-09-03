package com.javacreed.examples.concurrency.part1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * This class has two fields, {@link #a} and {@link #b}, which should always have the same value. The value
 * can only be set through one method: {@link #setValue(int)}.
 * 
 * @author Albert
 * 
 */
@ThreadSafe
public class BetterValuePair {

  @GuardedBy("this")
  private int a;

  @GuardedBy("this")
  private int b;

  public void copy(final BetterValuePair other) {
    final int a, b;
    synchronized (other) {
      a = other.a;
      b = other.b;
    }

    synchronized (this) {
      this.a = a;
      this.b = b;
    }
  }

  public synchronized void setValue(final int value) {
    this.a = value;
    this.b = value;
  }

  @Override
  public synchronized String toString() {
    return String.format("a: %d and b: %d", a, b);
  }

}
