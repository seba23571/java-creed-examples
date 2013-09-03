package com.javacreed.examples.concurrency.part1;

/**
 * This class has two fields, {@link #a} and {@link #b}, which should always have the same value. The value
 * can only be set through one method: {@link #setValue(int)}.
 * 
 * @author Albert
 * 
 */
public class ValuePairDeadLock {

  private int a;
  private int b;

  public synchronized void copy(final ValuePairDeadLock other) {
    synchronized (other) {
      this.a = other.a;
      this.b = other.b;
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
