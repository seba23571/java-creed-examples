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
package com.javacreed.examples.concurrent.appendix.a;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("A.3")
public class ThreadID {

  private static class ThreadLocalID extends ThreadLocal<Integer> {
    @Override
    protected synchronized Integer initialValue() {
      return ThreadID.nextID++;
    }
  }

  public static int get() {
    return ThreadID.threadID.get();
  }

  public static void set(final int index) {
    ThreadID.threadID.set(index);
  }

  private static volatile int nextID = 0;

  private static ThreadLocalID threadID = new ThreadLocalID();
}
