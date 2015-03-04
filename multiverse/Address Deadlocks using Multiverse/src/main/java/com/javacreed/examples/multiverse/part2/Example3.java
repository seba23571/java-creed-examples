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
package com.javacreed.examples.multiverse.part2;

import org.slf4j.Logger;

import com.javacreed.examples.multiverse.utils.LoggerUtils;

public class Example3 {

  public static final Logger LOGGER = LoggerUtils.getLogger(Example3.class);

  public static void main(final String[] args) throws InterruptedException {
    final StmAccount a = new StmAccount(10);
    final StmAccount b = new StmAccount(5);

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
    Example3.LOGGER.debug("Start both thread");
    t1.start();
    t2.start();

    // Wait for both threads to finish
    Example3.LOGGER.debug("Wait for both threads to finish (should be instantaneous)");
    t1.join();
    t2.join();

    Example3.LOGGER.debug("Both threads finished");
    Example3.LOGGER.debug("Account a: {}", a);
    Example3.LOGGER.debug("Account b: {}", b);
    Example3.LOGGER.debug("Done.");
  }

}
