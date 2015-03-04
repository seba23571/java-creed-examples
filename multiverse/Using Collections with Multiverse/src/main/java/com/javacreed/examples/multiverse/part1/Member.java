/*
 * #%L
 * Using Collections with Multiverse
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

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.callables.TxnVoidCallable;
import org.multiverse.api.references.TxnRef;

public class Member {

  private final TxnRef<Group> groupRef = StmUtils.newTxnRef();
  private final TxnRef<String> nameRef = StmUtils.newTxnRef();

  public String getName() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return nameRef.get(txn);
      }
    });
  }

  public void setGroup(final Txn txn, final Group group) {
    groupRef.set(txn, group);
  }

  public void setName(final String name) {
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        nameRef.set(txn, name);
      }
    });
  }

  @Override
  public String toString() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        final String name = nameRef.get(txn);
        if (name == null) {
          return "No name";
        }

        return name;
      }
    });
  }
}
