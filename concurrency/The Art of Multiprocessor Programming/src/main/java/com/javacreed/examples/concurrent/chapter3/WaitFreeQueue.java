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

import com.javacreed.examples.concurrent.extra.EmptyException;
import com.javacreed.examples.concurrent.extra.Figure;
import com.javacreed.examples.concurrent.extra.FullException;

/**
 * This is a correct implementation of a single-enqueuer/single-dequeuer FIFO queue.
 */
@Figure("3.3")
public class WaitFreeQueue<T> {

  private volatile int head = 0;
  private volatile int tail = 0;
  private final T[] items;

  @SuppressWarnings("unchecked")
  public WaitFreeQueue(final int capacity) {
    items = (T[]) new Object[capacity];
  }

  public T deq() throws EmptyException {
    if (tail - head == 0) {
      throw new EmptyException();
    }

    final T t = items[head % items.length];
    head++;
    return t;
  }

  public void end(final T t) throws FullException {
    if (tail - head == items.length) {
      throw new FullException();
    }
    items[tail % items.length] = t;
    tail++;
  }
}
