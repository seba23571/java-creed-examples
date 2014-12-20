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
package com.javacreed.examples.concurrent.chapter2;

import com.javacreed.examples.concurrent.appendix.a.ThreadID;
import com.javacreed.examples.concurrent.extra.Figure;

@Figure("2.7")
public class Filter implements Lock {

  /* In the book this property is not declared but then used within the methods */
  private final int n;
  private volatile int[] level;
  private volatile int[] victim;

  public Filter(final int n) {
    this.n = n;
    level = new int[n];
    victim = new int[n];

    /*
     * In the book the example also sets each element of the array to 0, which is their default value and thus not
     * required.
     */
  }

  @Override
  public void lock() {
    final int me = ThreadID.get();
    for (int i = 1; i < n; i++) {
      level[me] = i;
      victim[i] = me;

      // spin while conflicts exists
      outerLoop: while (victim[i] == me) {
        /* There exists some k other than me such that level[k] >= i */
        for (int k = 0; k < n; k++) {
          if (k != me && level[k] >= i) {
            continue outerLoop;
          }
        }
        break;
      }
    }
  }

  @Override
  public void unlock() {
    final int me = ThreadID.get();
    level[me] = 0;
  }

}
