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
package com.javacreed.examples.concurrent.extra;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ConcurrentTestTogether {

  /** The number of threads to use */
  private final int size;

  public ConcurrentTestTogether(final int size) {
    this.size = size;
  }

  public final void test() throws Throwable {
    /*
     * Any errors that occur within each thread are not thrown, but instead as added to this variable. At the end of the
     * test we verify that this is empty, otherwise we fail.
     */
    final AtomicReference<Throwable> exception = new AtomicReference<Throwable>();

    /* Makes sure that all threads start together */
    final SpinWaitCyclicBarrier cyclicBarrier = new SpinWaitCyclicBarrier(size);

    /* Creates and starts the threads which will be used to test the lock */
    final List<Thread> threads = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      final int index = i;
      final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          /* Start all threads together */
          cyclicBarrier.await();

          try {
            test(index);
          } catch (final Throwable e) {
            exception.compareAndSet(null, e);
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

    /* Verify that no error was thrown within each thread */
    if (exception.get() != null) {
      throw exception.get();
    }
  }

  protected abstract void test(int index) throws Throwable;
}
