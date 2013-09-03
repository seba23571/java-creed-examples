package com.javacreed.examples.concurrency.part1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

public class SimpleCounterTest {

  @Test
  public void test() throws Exception {

    final SimpleCounter counter = new SimpleCounter();
    counter.setValue(0);

    final AtomicReference<Exception> exception = new AtomicReference<>();

    final CyclicBarrier cyclicBarrier = new CyclicBarrier(12);

    final List<Thread> threads = new ArrayList<>(cyclicBarrier.getParties());
    for (int i = 0, size = cyclicBarrier.getParties(); i < size; i++) {
      final Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            cyclicBarrier.await();
            counter.incrementValue();
          } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            exception.compareAndSet(null, e);
          } catch (final BrokenBarrierException e) {
            exception.compareAndSet(null, e);
          }
        }
      });
      t.start();
      threads.add(t);
    }

    for (final Thread t : threads) {
      t.join();
    }

    if (exception.get() != null) {
      throw exception.get();
    }

    Assert.assertEquals(cyclicBarrier.getParties(), counter.getValue());
  }
}
