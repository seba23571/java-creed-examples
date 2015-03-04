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
package com.javacreed.examples.multiverse;

public class Account {

  private final TxnInteger balance;

  public Account(final int balance) {
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public void adjustBy(final int amount) throws InsufficientFundsException {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        balance.increment(amount);
        if (balance.get() < 0) {
          throw new InsufficientFundsException();
        }
      }
    });
  }

  @Override
  public String toString() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return String.format("Balance: %d", balance.get(txn));
      }
    });
  }

  public void transferBetween(final Account other, final int amount) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        adjustBy(-amount);
        other.adjustBy(amount);
      }
    });
  }
}
