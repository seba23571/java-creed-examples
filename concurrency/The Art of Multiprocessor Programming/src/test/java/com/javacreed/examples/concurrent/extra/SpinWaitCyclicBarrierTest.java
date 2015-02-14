package com.javacreed.examples.concurrent.extra;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class SpinWaitCyclicBarrierTest {

  @Test
  public void test() throws Throwable {
    final int size = 3;

    final AtomicInteger arrivedCounter = new AtomicInteger(size);
    final AtomicInteger proceededCounter = new AtomicInteger();

    final SpinWaitCyclicBarrier cyclicBarrier = new SpinWaitCyclicBarrier(size + 1);

    final List<Thread> waitingThreads = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          arrivedCounter.decrementAndGet();
          cyclicBarrier.await();
          proceededCounter.incrementAndGet();
        }
      });
      waitingThreads.add(thread);
      thread.start();
    }

    while (arrivedCounter.get() > 0) {}

    Assert.assertEquals(0, proceededCounter.intValue());
    Assert.assertEquals(1, cyclicBarrier.getNumberOfWaiting());
    cyclicBarrier.await();

    for (final Thread thread : waitingThreads) {
      thread.join();
    }

    Assert.assertEquals(size, proceededCounter.intValue());
  }

}
