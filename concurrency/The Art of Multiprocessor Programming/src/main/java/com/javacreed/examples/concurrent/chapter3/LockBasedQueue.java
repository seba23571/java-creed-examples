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
package com.javacreed.examples.concurrent.chapter3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.javacreed.examples.concurrent.extra.EmptyException;
import com.javacreed.examples.concurrent.extra.Figure;
import com.javacreed.examples.concurrent.extra.FullException;

@Figure("3.1")
public class LockBasedQueue<T> {

  private int head;
  private int tail;
  private final T[] items;
  private final Lock lock;

  @SuppressWarnings("unchecked")
  public LockBasedQueue(final int capacity) {
    head = 0;
    tail = 0;
    lock = new ReentrantLock();
    items = (T[]) new Object[capacity];
  }

  public T deq() throws EmptyException {
    lock.lock();
    try {
      if (tail == head) {
        throw new EmptyException();
      }

      final T t = items[head % items.length];
      head++;
      return t;
    } finally {
      lock.unlock();
    }
  }

  public void enq(final T t) throws FullException {
    lock.lock();
    try {
      if (tail - head == items.length) {
        throw new FullException();
      }

      items[tail % items.length] = t;
      tail++;
    } finally {
      lock.unlock();
    }
  }
}
