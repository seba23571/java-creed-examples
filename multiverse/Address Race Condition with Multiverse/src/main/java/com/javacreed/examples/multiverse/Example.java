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
package com.javacreed.examples.multiverse;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {

  private static class DelayedAccount extends Account {

    public DelayedAccount(final int balance) {
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

  public static final Logger LOGGER = LoggerFactory.getLogger(Example.class);

  public static void main(final String[] args) throws InterruptedException {

    final Account a = new DelayedAccount(10);
    final Account b = new DelayedAccount(5);

    // Transfer 5 from b to a using thread 1
    final Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          a.transferBetween(b, 10);
          Example.LOGGER.debug("Transfer complete");
        } catch (final InsufficientFundsException e) {
          Example.LOGGER.warn("Insufficient Funds!!");
        }
      }
    }, "T1");

    // Transfer 5 from a to b using thread 2
    final Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          a.transferBetween(b, 10);
          Example.LOGGER.debug("Transfer complete");
        } catch (final InsufficientFundsException e) {
          Example.LOGGER.warn("Insufficient Funds!!");
        }
      }
    }, "T2");

    // Start both threads
    Example.LOGGER.debug("Start both thread");
    t1.start();
    t2.start();

    // Wait for both threads to finish
    Example.LOGGER.debug("Wait for both threads to finish (should take a couple of seconds and one will fail)");
    t1.join();
    t2.join();

    Example.LOGGER.debug("Both threads finished");
    Example.LOGGER.debug("Account a: {}", a);
    Example.LOGGER.debug("Account b: {}", b);
    Example.LOGGER.debug("Done.");
  }

}
