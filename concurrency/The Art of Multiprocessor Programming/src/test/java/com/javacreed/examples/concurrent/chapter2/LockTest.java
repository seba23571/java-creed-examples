/*
 * #%L
 * The Art of Multiprocessor Programming
 * $Id:$
 * $HeadURL:$
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
package com.javacreed.examples.concurrent.chapter2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.javacreed.examples.concurrent.extra.ConcurrentTestTogether;

public class LockTest {

  @Test(timeout = 25000)
  public void test() throws Throwable {
    /* Keeps a reference to the thread that is in the critical section */
    final AtomicReference<Thread> criticalSection = new AtomicReference<Thread>();

    /* The number of threads to use */
    final int size = 30;

    /* The filter under test */
    final Lock lock = new Bakery(size);

    final ConcurrentTestTogether test = new ConcurrentTestTogether(size) {
      @Override
      protected void test(final int index) throws Throwable {

        /*
         * There is a small time window between when the lock is acquired and when we verify that only one thread is in
         * the critical section. This window allows for errors to be undetected. I do not know how to make these atomic
         * without affecting the test itself.
         */
        lock.lock();
        try {
          if (!criticalSection.compareAndSet(null, Thread.currentThread())) {
            throw new IllegalStateException("Found another thread in critical section");
          }

          /* Simulate some work */
          TimeUnit.MILLISECONDS.sleep(500);

          if (!criticalSection.compareAndSet(Thread.currentThread(), null)) {
            throw new IllegalStateException("Found another thread in critical section");
          }
        } catch (final InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          lock.unlock();
        }
      }
    };
    test.test();
  }
}
