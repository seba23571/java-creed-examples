/*
 * #%L
 * Address Deadlocks using Multiverse
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
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
package com.javacreed.examples.multiverse.part1;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.javacreed.examples.multiverse.utils.LoggerUtils;

/**
 * Please note that this class will deadlock and thus never exists. The sole purpose of the class is to demonstrate
 * deadlocks
 */
public class Example2 {

  /**
   * This class waits for 2 seconds which will cause deadlocks
   */
  private static class PojoAccountWithWait extends PojoAccount {
    public PojoAccountWithWait(final int balance) {
      super(balance);
    }

    @Override
    protected void waitABit() {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (final InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private static final Logger LOGGER = LoggerUtils.getLogger(Example2.class);

  public static void main(final String[] args) throws InterruptedException {
    final PojoAccount a = new PojoAccountWithWait(10);
    final PojoAccount b = new PojoAccountWithWait(5);

    // Transfer 5 from b to a using thread 1
    final Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        a.transferBetween(b, 5);
      }
    }, "T1");

    // Transfer 5 from a to b using thread 2
    final Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        b.transferBetween(a, 5);
      }
    }, "T2");

    // Start both threads
    Example2.LOGGER.debug("Start both thread");
    t1.start();
    t2.start();

    // Wait for both threads to finish
    Example2.LOGGER.debug("Wait for both threads to finish");
    Example2.LOGGER.warn("Please note this will wait forever.  Kill the program when you are done waiting.");
    t1.join();
    t2.join();

    Example2.LOGGER.debug("Both threads finished");
    Example2.LOGGER.debug("Account a: {}", a);
    Example2.LOGGER.debug("Account b: {}", b);
    Example2.LOGGER.debug("Done.");
  }

}
