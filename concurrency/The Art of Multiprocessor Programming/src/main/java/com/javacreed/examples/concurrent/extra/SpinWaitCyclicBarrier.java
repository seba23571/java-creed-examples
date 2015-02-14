package com.javacreed.examples.concurrent.extra;

import java.util.concurrent.atomic.AtomicInteger;

public class SpinWaitCyclicBarrier {

  private final AtomicInteger counter;
  private final int size;

  public SpinWaitCyclicBarrier(final int size) {
    this.size = size;
    counter = new AtomicInteger(size);
  }

  public void await() {
    counter.decrementAndGet();
    while (counter.get() > 0) {}// Spin Wait (this does not force the thread to cross the memory barrier)
  }

  public int getNumberOfWaiting() {
    return counter.get();
  }

  public int getSize() {
    return size;
  }

}
 