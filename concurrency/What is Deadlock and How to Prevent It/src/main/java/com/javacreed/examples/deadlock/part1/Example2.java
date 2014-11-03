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
package com.javacreed.examples.deadlock.part1;

import java.util.ArrayList;
import java.util.List;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example2 {

  public static double calculateAverage(final List<Integer> list) {
    double total = 0;
    for (int i = 0; i < list.size(); i++) {
      total += list.get(i);
      // This will cause the bug to manifest
      ThreadUtils.silentSleep(1);
    }

    return total / list.size();
  }

  public static void main(final String[] args) throws Exception {
    final List<Integer> list = new ArrayList<>();
    list.add(2);
    list.add(4);
    list.add(8);
    list.add(10);

    final Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (list) {
          final double average = Example2.calculateAverage(list);
          ThreadUtils.log("Average: %.2f", average);
        }
      }
    }, "Thread-A");
    threadA.start();

    final Thread threadB = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (list) {
          for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) * 2);
          }
        }
      }
    }, "Thread-B");
    threadB.start();

    /* Wait for the threads to stop */
    threadA.join();
    threadB.join();
  }

}
