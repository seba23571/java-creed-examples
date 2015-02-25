/*
 * #%L
 * Software Transansactional Memory Example
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
package com.javacreed.examples.multiverse.part1;

import net.jcip.annotations.GuardedBy;

public class PojoAccount {

  @GuardedBy("this")
  private int balance;

  public PojoAccount(final int balance) {
    this.balance = balance;
  }

  public synchronized void adjustBy(final int amount) {
    balance -= amount;
  }

  @Override
  public String toString() {
    return String.format("Balance: %d", balance);
  }

  public synchronized void transferBetween(final PojoAccount other, final int amount) {
    // This method call is added to simulate the deadlock
    waitABit();

    synchronized (other) {
      adjustBy(-amount);
      other.adjustBy(amount);
    }
  }

  /**
   * This method is added to simulate the deadlock
   */
  protected void waitABit() {}
}
