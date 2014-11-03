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
package com.javacreed.examples.deadlock.part4;

public class Example {

  public static void main(final String[] args) throws Exception {
    final Person personX = new Person("John", "Smith");
    final Person personY = new Person("John", "Smith");

    final Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        personX.copyFrom(personY);
      }
    }, "Thread-A");
    threadA.start();

    final Thread threadB = new Thread(new Runnable() {
      @Override
      public void run() {
        personY.copyFrom(personX);
      }
    }, "Thread-B");
    threadB.start();

    /* Wait for the threads to stop */
    threadA.join();
    threadB.join();
  }
}
