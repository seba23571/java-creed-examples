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
package com.javacreed.examples.multiverse.part3;

import java.util.concurrent.TimeUnit;

import net.jcip.annotations.GuardedBy;

public class PojoMember {

  @GuardedBy("this")
  private PojoGroup group;

  @GuardedBy("this")
  private double score;

  public synchronized double getScore() {
    return score;
  }

  public synchronized boolean isAboveAverage() {
    // This method call is added to simulate the deadlock
    waitABit();

    return score > group.calculateAverage();
  }

  public synchronized void setGroup(final PojoGroup group) {
    this.group = group;
  }

  public synchronized void setScore(final double score) {
    this.score = score;
  }

  /**
   * This method is added to simulate the deadlock
   */
  protected void waitABit() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
