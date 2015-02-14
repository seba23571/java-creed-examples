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

@Figure("2.9")
public class Bakery implements Lock {

  /* The example shown in the book does not have this property. It was added for consistency */
  private final int n;

  private volatile boolean[] flag;

  /* Used an 'int' instead of 'Label' as the purpose of this label is to provide an ordering mechanism */
  private volatile int[] label;

  public Bakery(final int n) {
    this.n = n;
    flag = new boolean[n];
    label = new int[n];

    /*
     * The example shown in the book also sets each element of the arrays to 0 and false respectively, which is their
     * default value and thus not required.
     */
  }

  @Override
  public void lock() {
    final int i = ThreadID.get();
    flag[i] = true;
    label[i] = nextMax();

    outerLoop: while (true) {
      for (int k = 0; k < n; k++) {
        /* wait if the process has a number and comes ahead of us */
        if (flag[k] && (label[k] < label[i] || label[k] == label[i] && k < i)) {
          // wait for the thread with the lower label value to go before us
          continue outerLoop;
        }
      }

      break;
    }
  }

  /*
   * The example shown in the book makes reference to a method named 'max(int ... values)' which method returns the
   * maximum value of an array
   */
  private int nextMax() {
    int max = 0;
    for (final int l : label) {
      max = Math.max(max, l);
    }
    return max + 1;
  }

  @Override
  public void unlock() {
    final int me = ThreadID.get();
    flag[me] = false;
  }

}
