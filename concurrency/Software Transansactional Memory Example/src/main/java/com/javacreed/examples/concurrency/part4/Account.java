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
package com.javacreed.examples.concurrency.part4;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;
import org.multiverse.api.references.TxnRef;

import java.util.Date;

public class Account {

  private final TxnRef<Date> lastUpdate;
  private final TxnInteger balance;

  public Account(final int balance) {
    this.lastUpdate = StmUtils.newTxnRef(new Date());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public void adjustBy(final int amount) {
    adjustBy(amount, new Date());
  }

  public void adjustBy(final int amount, final Date date) {
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

  @Override
  public String toString() {
    final String[] formatted = new String[1];
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        formatted[0] = String.format("%d (as of %tF %<tT)", balance.get(), lastUpdate.get());
      }
    });
    return formatted[0];
  }
}
