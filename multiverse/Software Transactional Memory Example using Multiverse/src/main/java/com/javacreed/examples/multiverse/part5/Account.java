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
package com.javacreed.examples.multiverse.part5;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class Account {

  private final TxnLong lastUpdate;
  private final TxnInteger balance;

  public Account(final int balance) {
    lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public void adjustBy(final int amount) {
    adjustBy(amount, System.currentTimeMillis());
  }

  public void adjustBy(final int amount, final long date) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        balance.increment(amount);
        lastUpdate.set(date);

        if (balance.get() < 0) {
          throw new IllegalStateException("Not enough money");
        }
      }
    });
  }

  public int getBalance(final Txn txn) {
    return balance.get(txn);
  }

  @Override
  public String toString() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return String.format("%d (as of %tF %<tT)", balance.get(txn), lastUpdate.get(txn));
      }
    });
  }
}
