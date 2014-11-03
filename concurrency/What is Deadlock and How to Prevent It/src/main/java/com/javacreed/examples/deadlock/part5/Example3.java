/*
 * #%L
 * What is Deadlock and How to Prevent It?
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
package com.javacreed.examples.deadlock.part5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example3 {

  public static void main(final String[] args) throws Exception {
    final Lock lock = new ReentrantLock();

    final Thread thread1 = Example3.runInThread(lock, "Thread-1");
    final Thread thread2 = Example3.runInThread(lock, "Thread-2");

    thread1.join();
    thread2.join();

    ThreadUtils.log("Done");
  }

  private static Thread runInThread(final Lock lock, final String name) {
    final Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          // Wait a second before it gives up
          if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
              ThreadUtils.log("Working...");
              ThreadUtils.silentSleep(100);
            } finally {
              lock.unlock();
            }
          } else {
            ThreadUtils.log("Lock taken.  I'm giving up");
          }
        } catch (final InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }, name);
    thread.start();
    return thread;
  }
}
