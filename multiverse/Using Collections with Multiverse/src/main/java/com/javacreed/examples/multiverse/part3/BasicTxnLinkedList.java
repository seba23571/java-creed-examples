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

import org.multiverse.api.GlobalStmInstance;
import org.multiverse.api.Stm;
import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.TxnThreadLocal;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.collections.TxnIterator;
import org.multiverse.api.collections.TxnList;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;
import org.multiverse.api.references.TxnRefFactory;
import org.multiverse.collections.AbstractTxnCollection;
import org.multiverse.collections.AbstractTxnIterator;

public class BasicTxnLinkedList<E> extends AbstractTxnCollection<E> implements TxnList<E> {

  private static class Element<E> {
    private final TxnRef<Element<E>> next;
    private final TxnRef<Element<E>> previous;
    private final TxnRef<E> value;

    private Element(final TxnRefFactory txnRefFactory, final E value) {
      this.next = txnRefFactory.newTxnRef(null);
      this.previous = txnRefFactory.newTxnRef(null);
      this.value = txnRefFactory.newTxnRef(value);
    }

    @Override
    public String toString() {
      return StmUtils.atomic(new TxnCallable<String>() {
        @Override
        public String call(final Txn txn) throws Exception {
          return String.valueOf(value.toString(txn));
        }
      });
    }
  }

  private static class ReadonlyLinkedListIterator<E> extends AbstractTxnIterator<E> {

    private final TxnRef<Element<E>> node;

    private ReadonlyLinkedListIterator(final TxnRefFactory txnRefFactory, final Element<E> head) {
      this.node = txnRefFactory.newTxnRef(head);
    }

    @Override
    public boolean hasNext(final Txn txn) {
      return !node.isNull(txn);
    }

    @Override
    public E next(final Txn txn) {
      final E value = node.get(txn).value.get(txn);
      node.set(txn, node.get(txn).next.get(txn));
      return value;
    }

    @Override
    public void remove(final Txn txn) {
      throw new UnsupportedOperationException();
    }
  }

  private final TxnInteger sizeRef;
  private final TxnRef<Element<E>> headRef;
  private final TxnRef<Element<E>> tailRef;

  public BasicTxnLinkedList() {
    this(GlobalStmInstance.getGlobalStmInstance());
  }

  public BasicTxnLinkedList(final Stm stm) {
    super(stm);

    this.sizeRef = stm.getDefaultRefFactory().newTxnInteger(0);
    this.headRef = stm.getDefaultRefFactory().newTxnRef(null);
    this.tailRef = stm.getDefaultRefFactory().newTxnRef(null);
  }

  @Override
  public boolean add(final Txn txn, final E element) {
    if (element == null) {
      throw new NullPointerException();
    }

    final int s = sizeRef.get(txn);
    if (s == Integer.MAX_VALUE) {
      return false;
    }

    final Element<E> node = new Element<E>(defaultRefFactory, element);
    if (s == 0) {
      headRef.set(txn, node);
      tailRef.set(txn, node);
    } else {
      node.previous.set(txn, tailRef.get(txn));
      tailRef.get(txn).next.set(txn, node);
      tailRef.set(txn, node);
    }
    sizeRef.increment(txn);
    return true;
  }

  @Override
  public void clear(final Txn txn) {
    if (sizeRef.get(txn) == 0) {
      return;
    }

    sizeRef.set(txn, 0);
    headRef.set(txn, null);
    tailRef.set(txn, null);
  }

  @Override
  public boolean contains(final Txn txn, final Object element) {
    return indexOf(txn, element) != -1;
  }

  @Override
  public E get(final int index) {
    return get(TxnThreadLocal.getThreadLocalTxn(), index);
  }

  @Override
  public E get(final Txn txn, final int index) {
    return getElementAt(txn, index).value.get(txn);
  }

  private Element<E> getElementAt(final Txn txn, final int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException();
    }

    final int size = sizeRef.get(txn);
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }

    if (index < size >> 1) {
      int i = 0;
      Element<E> node = headRef.get(txn);
      while (true) {
        if (i == index) {
          return node;
        }
        node = node.next.get(txn);
        i++;
      }
    } else {
      int i = size - 1;
      Element<E> node = tailRef.get(txn);
      while (true) {
        if (i == index) {
          return node;
        }
        node = node.previous.get(txn);
        i--;
      }
    }
  }

  @Override
  public int indexOf(final Object element) {
    return indexOf(TxnThreadLocal.getThreadLocalTxn(), element);
  }

  @Override
  public int indexOf(final Txn txn, final Object element) {
    if (element == null) {
      return -1;
    }

    int index = 0;
    Element<E> node = headRef.get(txn);
    while (node != null) {
      if (node.value.get(txn).equals(element)) {
        return index;
      }
      node = node.next.get(txn);
      index++;
    }

    return -1;
  }

  @Override
  public TxnIterator<E> iterator(final Txn txn) {
    return new ReadonlyLinkedListIterator<>(stm.getDefaultRefFactory(), headRef.get(txn));
  }

  @Override
  public int lastIndexOf(final Object element) {
    return lastIndexOf(TxnThreadLocal.getThreadLocalTxn(), element);
  }

  @Override
  public int lastIndexOf(final Txn txn, final Object element) {
    if (element == null) {
      return -1;
    }

    int index = sizeRef.get(txn) - 1;
    Element<E> node = tailRef.get(txn);
    while (node != null) {
      if (node.value.get(txn).equals(element)) {
        return index;
      }
      node = node.previous.get(txn);
      index--;
    }

    return -1;
  }

  @Override
  public E remove(final int index) {
    return remove(TxnThreadLocal.getThreadLocalTxn(), index);
  }

  @Override
  public E remove(final Txn txn, final int index) {
    final Element<E> node = getElementAt(txn, index);
    if (node == null) {
      return null;
    }

    removeAndRewire(txn, node);
    return node.value.get(txn);
  }

  @Override
  public boolean remove(final Txn txn, final Object element) {
    if (element == null) {
      return false;
    }

    for (Element<E> node = headRef.get(txn); node != null; node = node.next.get(txn)) {
      if (node.value.get(txn).equals(element)) {
        removeAndRewire(txn, node);
        return true;
      }
    }

    return false;
  }

  private void removeAndRewire(final Txn txn, final Element<E> node) {
    final Element<E> previousNode = node.previous.get(txn);
    final Element<E> nextNode = node.next.get(txn);

    if (previousNode == null) {
      // Update head element
      headRef.set(txn, node.next.get(txn));
    } else {
      previousNode.next.set(txn, nextNode);
    }

    if (nextNode == null) {
      // Update tail element
      tailRef.set(txn, previousNode);
    } else {
      nextNode.previous.set(txn, previousNode);
    }

    node.previous.set(txn, null);
    node.next.set(txn, null);
    sizeRef.decrement(txn);
  }

  @Override
  public E set(final int index, final E element) {
    return set(TxnThreadLocal.getThreadLocalTxn(), index, element);
  }

  @Override
  public E set(final Txn tx, final int index, final E element) {
    return getElementAt(tx, index).value.getAndSet(tx, element);
  }

  @Override
  public int size(final Txn tx) {
    return sizeRef.get(tx);
  }

  @Override
  public String toString(final Txn tx) {
    final int s = size(tx);
    if (s == 0) {
      return "[]";
    }

    final StringBuffer sb = new StringBuffer();
    sb.append('[');
    Element<E> node = headRef.get(tx);
    do {
      sb.append(node.value.get(tx));
      node = node.next.get(tx);
      if (node != null) {
        sb.append(", ");
      }
    } while (node != null);
    sb.append(']');
    return sb.toString();
  }

}
