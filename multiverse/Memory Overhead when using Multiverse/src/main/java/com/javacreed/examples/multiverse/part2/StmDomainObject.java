/*
 * #%L
 * Memory Overhead when using Multiverse
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
package com.javacreed.examples.multiverse.part2;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.references.TxnRef;

public class StmDomainObject {

  private final TxnRef<String> value;

  public StmDomainObject(final long value) {
    this.value = StmUtils.newTxnRef(String.valueOf(value));
  }

  public String getValue() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return value.get(txn);
      }
    });
  }

  public void setValue(final String value) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        StmDomainObject.this.value.set(value);
      }
    });
  }
}
