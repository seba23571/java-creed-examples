/*
 * #%L
 * Understanding Theads Monitors and Locks
 * $Id:$
 * $HeadURL$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
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
package com.javacreed.examples.concurrency.part1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

public class ThreadSafeCounterTest {

  @Test
  public void test() throws Exception {

    final ThreadSafeCounter counter = new ThreadSafeCounter();
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
