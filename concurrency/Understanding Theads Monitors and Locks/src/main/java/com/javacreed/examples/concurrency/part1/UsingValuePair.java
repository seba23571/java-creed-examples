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

public class UsingValuePair {
  public static void main(final String[] args) throws InterruptedException {
    final ValuePair object1 = new ValuePair();
    object1.setValue(1);

    final ValuePair object2 = new ValuePair();
    object2.setValue(5);

    final Thread thread1 = new Thread("Thread 1") {
      @Override
      public void run() {
        object1.copy(object2);
      }
    };

    final Thread thread2 = new Thread("Thread 2") {
      @Override
      public void run() {
        object2.setValue(12);
      }
    };

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
    
    System.out.println(object1);
  }
}
