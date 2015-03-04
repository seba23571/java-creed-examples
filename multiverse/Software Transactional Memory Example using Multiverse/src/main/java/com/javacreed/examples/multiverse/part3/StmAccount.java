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
package com.javacreed.examples.multiverse.part3;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.callables.TxnIntCallable;
import org.multiverse.api.callables.TxnLongCallable;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class StmAccount {

  private final TxnLong lastUpdate;
  private final TxnInteger balance;

  public StmAccount(final int balance) {
    lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public int getBalance() {
    return StmUtils.atomic(new TxnIntCallable() {
      @Override
      public int call(final Txn txn) throws Exception {
        return balance.get(txn);
      }
    });
  }

  public long getLastUpdate() {
    return StmUtils.atomic(new TxnLongCallable() {
      @Override
      public long call(final Txn txn) throws Exception {
        return lastUpdate.get(txn);
      }
    });
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
