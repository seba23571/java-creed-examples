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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockTest {

  /** The logger is useful to visualise how the threads work and how these enter the critical section. */
  private static final Logger LOGGER = LoggerFactory.getLogger(LockTest.class);

  @Test
  public void test() throws Throwable {

    /* Keeps a reference to the thread that is in the critical section */
    final AtomicReference<Thread> criticalSection = new AtomicReference<Thread>();

    /*
     * Any errors that occur within each thread are not thrown, but instead as added to this variable. At the end of the
     * test we verify that this is empty, otherwise we fail.
     */
    final AtomicReference<Throwable> exception = new AtomicReference<Throwable>();

    /* The number of threads to use */
    final int size = 30;
    LockTest.LOGGER.debug("Initiating the test with {} threads", size);

    /* Makes sure that all threads start together */
    final CyclicBarrier cyclicBarrier = new CyclicBarrier(size);

    /* The filter under test */
    final Lock lock = new Bakery(size);

    /* Creates and starts the threads which will be used to test the lock */
    final List<Thread> threads = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          // Start all threads together
          try {
            LockTest.LOGGER.debug("Waiting for the other threads to get to this point");
            cyclicBarrier.await(500, TimeUnit.MILLISECONDS);
          } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            exception.compareAndSet(null, new IllegalStateException("Failed to wait for the other threads", e));
            return;
          }

          /*
           * There is a small time window between when the lock is acquired and when we verify that only one thread is
           * in the critical section. This window allows for errors to be undetected. I do not know how to make these
           * atomic without affecting the test itself.
           */
          LockTest.LOGGER.debug("Attempting to acquire lock");
          lock.lock();
          LockTest.LOGGER.debug("-------------------------------------------------------------------");
          LockTest.LOGGER.debug("Critical Section");
          LockTest.LOGGER.debug("-------------------------------------------------------------------");
          try {
            LockTest.LOGGER.debug("Verifying that no other thread is in the critical section");
            if (!criticalSection.compareAndSet(null, Thread.currentThread())) {
              exception.compareAndSet(null, new IllegalStateException("Found another thread in critical section"));
              return;
            }

            /* Simulate some work */
            LockTest.LOGGER.debug("Working for 500 milli-seconds");
            TimeUnit.MILLISECONDS.sleep(500);
            LockTest.LOGGER.debug("Working complete");

            LockTest.LOGGER.debug("Verifying that no other thread is in the critical section");
            if (!criticalSection.compareAndSet(Thread.currentThread(), null)) {
              exception.compareAndSet(null, new IllegalStateException("Found another thread in critical section"));
            }
          } catch (final InterruptedException e) {
            LockTest.LOGGER.debug("Interrupted");
            Thread.currentThread().interrupt();
          } finally {
            LockTest.LOGGER.debug("-------------------------------------------------------------------");
            lock.unlock();
          }
        }
      });
      thread.start();
      threads.add(thread);
    }

    /* Wait for all threads to finish */
    for (final Thread thread : threads) {
      thread.join();
    }
    LockTest.LOGGER.debug("All threads are ready");

    /* Verify that no error was thrown within each thread */
    if (exception.get() != null) {
      throw exception.get();
    }

    LockTest.LOGGER.debug("No errors were detected");
  }
}
