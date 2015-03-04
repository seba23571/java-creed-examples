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
import org.multiverse.api.callables.TxnDoubleCallable;
import org.multiverse.api.collections.TxnList;

public class Accounts {

  private final TxnList<Account> list;

  public Accounts() {
    list = StmUtils.newTxnLinkedList();
  }

  public void addAccount(final Account account) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        list.add(account);
      }
    });
  }

  public double calculateAverageBalance() {
    return StmUtils.atomic(new TxnDoubleCallable() {
      @Override
      public double call(final Txn txn) throws Exception {
        if (list.isEmpty(txn)) {
          return 0;
        }

        final int size = list.size(txn);
        double total = 0;
        // Iteration is not supported by Multiverse at this stage
        for (int i = 0; i < size; i++) {
          final Account account = list.get(txn, i);
          total += account.getBalance(txn);
        }
        return total / size;
      }
    });
  }
}
