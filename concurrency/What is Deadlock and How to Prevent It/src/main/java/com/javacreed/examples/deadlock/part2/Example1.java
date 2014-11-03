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
package com.javacreed.examples.deadlock.part2;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example1 {

  public static void main(final String[] args) throws Exception {

    final Object lockX = new Object();
    final Object lockY = new Object();

    final Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        ThreadUtils.log("Acquire lock-X");
        synchronized (lockX) {
          ThreadUtils.log("Acquire lock-Y");
          synchronized (lockY) {
            ThreadUtils.log("Both locks are acquired");
          }
          ThreadUtils.log("Release lock-Y");
        }
        ThreadUtils.log("Release lock-X");
      }
    }, "Thread-A");
    threadA.start();

    /* Wait for the thread to stop */
    threadA.join();
  }
}
