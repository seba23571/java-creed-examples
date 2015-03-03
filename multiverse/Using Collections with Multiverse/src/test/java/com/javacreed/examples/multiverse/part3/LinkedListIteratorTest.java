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
package com.javacreed.examples.multiverse.part3;

import org.junit.Assert;
import org.junit.Test;
import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnVoidCallable;
import org.multiverse.api.collections.TxnIterator;
import org.multiverse.api.collections.TxnList;

public class LinkedListIteratorTest extends AbstractLinkedListTest {

  @Test
  public void testBasic() {
    final TxnList<String> list = createList();

    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          list.add(s);
        }

        final TxnIterator<String> iterator = list.iterator();
        for (int i = 0; i < array.length; i++) {
          Assert.assertTrue(iterator.hasNext());
          Assert.assertEquals(array[i], iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());
      }
    });
  }

  @Test
  public void testForEachLoop() {
    final TxnList<String> list = createList();

    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          list.add(s);
        }

        int i = 0;
        for (final String s : list) {
          Assert.assertEquals(array[i++], s);
        }
      }
    });
  }

}
