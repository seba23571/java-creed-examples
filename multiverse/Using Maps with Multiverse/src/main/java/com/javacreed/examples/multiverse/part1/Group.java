/*
 * #%L
 * Using Maps with Multiverse
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
import org.multiverse.api.callables.TxnIntCallable;
import org.multiverse.api.callables.TxnVoidCallable;
import org.multiverse.api.collections.TxnMap;

public class Group {

  private final TxnMap<String, Member> members = StmUtils.newTxnHashMap();

  public void addMember(final Member member) {
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        members.put(txn, member.getName(), member);
        member.setGroup(txn, Group.this);
      }
    });
  }

  public int size() {
    return StmUtils.atomic(new TxnIntCallable() {
      @Override
      public int call(final Txn txn) throws Exception {
        return members.size(txn);
      }
    });
  }
}
