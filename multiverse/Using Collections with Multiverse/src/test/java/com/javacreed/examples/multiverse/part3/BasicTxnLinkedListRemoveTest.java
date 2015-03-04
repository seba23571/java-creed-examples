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
import org.multiverse.api.collections.TxnList;

public class BasicTxnLinkedListRemoveTest extends AbstractLinkedListTest {

  private static class DieException extends RuntimeException {
    private static final long serialVersionUID = -5591183956332330649L;
  }

  private <E> void assertContents(final Txn txn, final TxnList<E> list,
      @SuppressWarnings("unchecked") final E... expected) {
    // Make sure that the list is of the expected size
    Assert.assertEquals(expected.length, list.size());

    // Validate by using get
    for (int i = 0; i < expected.length; i++) {
      Assert.assertEquals(expected[i], list.get(i));
    }

    // Validate using iterator
    int i = 0;
    for (final E s : list) {
      Assert.assertEquals(expected[i++], s);
    }

  }

  @Test
  public void testRemoveByElement() {
    final TxnList<String> list = createList();

    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          list.add(s);
        }

        // Removes 'B'
        list.remove("B");
        assertContents(txn, list, "A", "C");

        // Removes 'A'
        list.remove("A");
        assertContents(txn, list, "C");

        // Removes 'C'
        list.remove("C");
        assertContents(txn, list);

        // All elements were removed
        Assert.assertTrue(list.isEmpty());
      }
    });
  }

  @Test
  public void testRemoveByIndex() {
    final TxnList<String> list = createList();

    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          list.add(s);
        }

        // Removes 'B'
        list.remove(1);
        assertContents(txn, list, "A", "C");

        // Removes 'A'
        list.remove(0);
        assertContents(txn, list, "C");

        // Removes 'C'
        list.remove(0);
        assertContents(txn, list);

        // All elements were removed
        Assert.assertTrue(list.isEmpty());
      }
    });
  }

  @Test
  public void testRemoveWithException() {
    final TxnList<String> list = createList();

    // Fill List
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          list.add(s);
        }
      }
    });

    // Execute an action which fails
    try {
      StmUtils.atomic(new TxnVoidCallable() {
        @Override
        public void call(final Txn txn) throws Exception {
          list.remove(1);
          assertContents(txn, list, "A", "C");

          // Fail after the element is removed (and the remove is confirmed)
          throw new DieException();
        }
      });
      Assert.fail("This should have failed");
    } catch (final DieException e) {}

    // Make sure that the list is rolled back
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        assertContents(txn, list, "A", "B", "C");
      }
    });
  }
}
