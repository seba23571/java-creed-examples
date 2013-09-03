package com.javacreed.examples.concurrency.part1;

public class SimpleCounter {

  private int value;

  public int getValue() {
    return value;
  }

  public void incrementValue() {
    final int temp = value;
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    final int newValue = temp + 1;
    value = newValue;
  }

  public void setValue(final int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
