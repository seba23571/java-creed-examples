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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnVoidCallable;
import org.multiverse.api.collections.TxnMap;

public class BasicTxnHashMapRemoveTest extends AbstractHashMapTest {

  private static class DieException extends RuntimeException {
    private static final long serialVersionUID = -5591183956332330649L;
  }

  private <E> void assertContents(final Txn txn, final TxnMap<E, E> map,
      @SuppressWarnings("unchecked") final E... expected) {
    // Make sure that the list is of the expected size
    Assert.assertEquals(expected.length, map.size());

    // Validate by using get
    for (int i = 0; i < expected.length; i++) {
      Assert.assertEquals(expected[i], map.get(expected[i]));
    }

    // Validate using iterator
    final Set<E> keys = new HashSet<>(Arrays.asList(expected));
    for (final Entry<E, E> entry : map.entrySet(txn)) {
      Assert.assertTrue(String.format("Missing key: '%s'", entry.getKey()), keys.remove(entry.getKey()));
    }
  }

  @Test
  public void testRemoveByKey() {
    final TxnMap<String, String> map = createMap();

    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          map.put(s, s);
        }

        // Removes 'B'
        Assert.assertEquals("B", map.remove("B"));
        assertContents(txn, map, "A", "C");

        // Removes 'A'
        map.remove("A");
        assertContents(txn, map, "C");

        // Removes 'C'
        map.remove("C");
        assertContents(txn, map);

        // All elements were removed
        Assert.assertTrue(map.isEmpty());
      }
    });
  }

  @Test
  public void testRemoveWithException() {
    final TxnMap<String, String> map = createMap();

    // Fill List
    StmUtils.atomic(new TxnVoidCallable() {
      @Override
      public void call(final Txn txn) throws Exception {
        final String[] array = { "A", "B", "C" };
        for (final String s : array) {
          map.put(s, s);
        }
      }
    });

    // Execute an action which fails
    try {
      StmUtils.atomic(new TxnVoidCallable() {
        @Override
        public void call(final Txn txn) throws Exception {
          map.remove("B");
          assertContents(txn, map, "A", "C");

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
        assertContents(txn, map, "A", "B", "C");
      }
    });
  }
}
