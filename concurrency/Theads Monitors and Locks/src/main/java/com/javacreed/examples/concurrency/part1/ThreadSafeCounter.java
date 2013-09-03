package com.javacreed.examples.concurrency.part1;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ThreadSafeCounter {

  private int value;

  public synchronized int getValue() {
    return value;
  }

  public synchronized void incrementValue() {
    final int temp = value;
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    final int newValue = temp + 1;
    value = newValue;
  }

  public synchronized void setValue(final int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
