package com.javacreed.examples.multiverse.part3;

import java.util.Map;
import java.util.NoSuchElementException;

import org.multiverse.api.Stm;
import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.collections.TxnCollection;
import org.multiverse.api.collections.TxnIterator;
import org.multiverse.api.collections.TxnSet;
import org.multiverse.api.exceptions.TodoException;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;
import org.multiverse.api.references.TxnRefFactory;
import org.multiverse.collections.AbstractTxnCollection;
import org.multiverse.collections.AbstractTxnIterator;
import org.multiverse.collections.AbstractTxnMap;

public class BasicTxnHashMap<K, V> extends AbstractTxnMap<K, V> {

    private abstract static class AbstractEntrySetIterator<R, L, W> extends AbstractTxnIterator<R> {

        private final BasicTxnHashMap<L, W> parent;

        /** The current index into the array of buckets */
        private final TxnInteger hashIndexRef;

        /** The last returned entry */
        private final TxnRef<BasicEntry<L, W>> lastRef;

        /** The next entry */
        private final TxnRef<BasicEntry<L, W>> nextRef;

        protected AbstractEntrySetIterator(final Txn txn, final TxnRefFactory txnRefFactory, final BasicTxnHashMap<L, W> parent) {
            this.parent = parent;

            final TxnRef<TxnRef<BasicEntry<L, W>>[]> table = parent.tableRef;
            int i = table.get(txn).length;
            BasicEntry<L, W> next = null;
            while (i > 0 && next == null) {
                next = table.get(txn)[--i].get(txn);
            }

            this.lastRef = txnRefFactory.newTxnRef(null);
            this.nextRef = txnRefFactory.newTxnRef(next);
            this.hashIndexRef = txnRefFactory.newTxnInteger(i);
        }

        @Override
        public boolean hasNext(final Txn txn) {
            return !nextRef.isNull(txn);
        }

        protected abstract R next(Entry<L, W> entry);

        @Override
        public R next(final Txn txn) {
            final BasicEntry<L, W> newCurrent = nextRef.get(txn);
            if (newCurrent == null) {
                throw new NoSuchElementException();
            }

            final TxnRef<TxnRef<BasicEntry<L, W>>[]> tableRef = parent.tableRef;
            int i = hashIndexRef.get(txn);
            BasicEntry<L, W> n = newCurrent.nextRef.get(txn);
            while (n == null && i > 0) {
                n = tableRef.get(txn)[--i].get(txn);
            }

            nextRef.set(txn, n);
            hashIndexRef.set(txn, i);
            lastRef.set(txn, newCurrent);
            return next(newCurrent);
        }

        @Override
        public void remove(final Txn txn) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return StmUtils.atomic(new TxnCallable<String>() {
                @Override
                public String call(final Txn txn) throws Exception {
                    if (lastRef.isNull(txn)) {
                        return "Iterator[]";
                    }

                    return "Iterator[" + lastRef.get(txn).getKey() + "=" + lastRef.get(txn).getValue() + "]";
                }
            });
        }

    }

    private static class BasicEntry<L, W> implements Map.Entry<L, W> {
        final L key;
        final int hash;
        final TxnRef<W> valueRef;
        final TxnRef<BasicEntry<L, W>> nextRef;

        private BasicEntry(final TxnRefFactory txnRefFactory, final int hash, final L key, final W value, final BasicEntry<L, W> next) {
            this.valueRef = txnRefFactory.newTxnRef(value);
            this.nextRef = txnRefFactory.newTxnRef(next);
            this.key = key;
            this.hash = hash;
        }

        @Override
        public final boolean equals(final Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            final Map.Entry<L, W> t = (Map.Entry<L, W>) object;
            final Object k1 = getKey();
            final Object k2 = t.getKey();
            if (k1 == k2 || k1 != null && k1.equals(k2)) {
                final Object v1 = getValue();
                final Object v2 = t.getValue();
                if (v1 == v2 || v1 != null && v1.equals(v2)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public final L getKey() {
            return key;
        }

        @Override
        public final W getValue() {
            return valueRef.get();
        }

        @Override
        public final int hashCode() {
            final W v = valueRef.get();
            return (key == null ? 0 : key.hashCode()) ^ (v == null ? 0 : v.hashCode());
        }

        @Override
        public final W setValue(final W newValue) {
            final W oldValue = valueRef.get();
            valueRef.set(newValue);
            return oldValue;
        }

        @Override
        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    private static class EntrySet<L, W> extends AbstractTxnCollection<Entry<L, W>> implements TxnSet<Entry<L, W>> {
        /** The parent map */
        protected final BasicTxnHashMap<L, W> parent;

        protected EntrySet(final Stm stm, final BasicTxnHashMap<L, W> parent) {
            super(stm);
            this.parent = parent;
        }

        @Override
        public boolean add(final Txn txn, final Entry<L, W> e) {
            return parent.put(txn, e.getKey(), e.getValue()) != null;
        }

        @Override
        public void clear(final Txn txn) {
            parent.clear(txn);
        }

        @Override
        public boolean contains(final Txn txn, final Object object) {
            if (object instanceof Map.Entry) {
                @SuppressWarnings("unchecked")
                final Map.Entry<L, W> t = (Map.Entry<L, W>) object;
                final Entry<L, W> match = parent.getEntry(txn, t.getKey());
                return match != null && match.equals(t);
            }
            return false;
        }

        @Override
        public TxnIterator<Entry<L, W>> iterator(final Txn txn) {
            return parent.createEntrySetIterator(txn);
        }

        @Override
        public boolean remove(final Txn txn, final Object object) {
            if (object instanceof Map.Entry == false) {
                return false;
            }
            if (contains(object) == false) {
                return false;
            }
            @SuppressWarnings("unchecked")
            final Map.Entry<L, W> t = (Map.Entry<L, W>) object;
            final Object key = t.getKey();
            parent.remove(txn, key);
            return true;
        }

        @Override
        public int size(final Txn txn) {
            return parent.size(txn);
        }

        @Override
        public String toString(final Txn txn) {
            return BasicTxnHashMap.formatIterator(txn, iterator(txn));
        }
    }

    private static class KeySet<L> extends AbstractTxnCollection<L> implements TxnSet<L> {

        private final BasicTxnHashMap<L, ?> parent;

        private KeySet(final Stm stm, final BasicTxnHashMap<L, ?> parent) {
            super(stm);
            this.parent = parent;
        }

        @Override
        public boolean add(final Txn txn, final L e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear(final Txn txn) {
            parent.clear(txn);
        }

        @Override
        public boolean contains(final Txn txn, final Object key) {
            return parent.containsKey(txn, key);
        }

        @Override
        public TxnIterator<L> iterator(final Txn txn) {
            return parent.createKeySetIterator(txn);
        }

        @Override
        public boolean remove(final Txn txn, final Object key) {
            return parent.remove(txn, key) != null;
        }

        @Override
        public int size(final Txn txn) {
            return parent.size(txn);
        }

        @Override
        public String toString(final Txn txn) {
            return BasicTxnHashMap.formatIterator(txn, iterator(txn));
        }
    }

    private static class ReadonlyEntrySetIterator<L, W> extends AbstractEntrySetIterator<Entry<L, W>, L, W> {

        protected ReadonlyEntrySetIterator(final Txn txn, final TxnRefFactory txnRefFactory, final BasicTxnHashMap<L, W> parent) {
            super(txn, txnRefFactory, parent);
        }

        @Override
        protected Entry<L, W> next(final Entry<L, W> entry) {
            return entry;
        }

    }

    private static class ReadonlyKeySetIterator<L, W> extends AbstractEntrySetIterator<L, L, W> {

        protected ReadonlyKeySetIterator(final Txn txn, final TxnRefFactory txnRefFactory, final BasicTxnHashMap<L, W> parent) {
            super(txn, txnRefFactory, parent);
        }

        @Override
        protected L next(final Entry<L, W> entry) {
            return entry.getKey();
        }

    }

    private static class ReadonlyValues<W> extends AbstractTxnCollection<W> {

        private final BasicTxnHashMap<?, W> parent;

        private ReadonlyValues(final Stm stm, final BasicTxnHashMap<?, W> parent) {
            super(stm);
            this.parent = parent;
        }

        @Override
        public boolean add(final Txn txn, final W e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear(final Txn txn) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(final Txn txn, final Object value) {
            return parent.containsValue(txn, value);
        }

        @Override
        public TxnIterator<W> iterator(final Txn txn) {
            return parent.createValuesIterator(txn);
        }

        @Override
        public boolean remove(final Txn txn, final Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size(final Txn txn) {
            return parent.size(txn);
        }

        @Override
        public String toString(final Txn txn) {
            return BasicTxnHashMap.formatIterator(txn, iterator(txn));
        }
    }

    private static class ReadonlyValuesIterator<L, W> extends AbstractEntrySetIterator<W, L, W> {

        protected ReadonlyValuesIterator(final Txn txn, final TxnRefFactory txnRefFactory, final BasicTxnHashMap<L, W> parent) {
            super(txn, txnRefFactory, parent);
        }

        @Override
        protected W next(final Entry<L, W> entry) {
            return entry.getValue();
        }

    }

    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The maximum capacity, used if a higher value is implicitly specified by either of the constructors with arguments. MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    private static <T> String formatIterator(final Txn txn, final TxnIterator<T> iterator) {
        final StringBuilder formatted = new StringBuilder();
        formatted.append("[");

        formatted.append(iterator.next(txn));
        while (iterator.hasNext()) {
            formatted.append(", ");
            formatted.append(iterator.next(txn));
        }

        formatted.append("]");
        return formatted.toString();
    }

    private static int indexFor(final int hash, final int capacity) {
        return hash & capacity - 1;
    }

    private final TxnInteger sizeRef;

    private final TxnRef<TxnRef<BasicEntry<K, V>>[]> tableRef;

    private final TxnInteger thresholdRef;

    private final TxnRef<TxnSet<Entry<K, V>>> entrySetRef;

    private final TxnRef<TxnSet<K>> keySetRef;

    private final TxnRef<TxnCollection<V>> valuesRef;

    /**
     * The load factor for the hash table.
     * 
     * @serial
     */
    private final float loadFactor;

    public BasicTxnHashMap(final Stm stm) {
        super(stm);
        this.sizeRef = defaultRefFactory.newTxnInteger(0);
        this.thresholdRef = defaultRefFactory.newTxnInteger((int) (BasicTxnHashMap.DEFAULT_INITIAL_CAPACITY * BasicTxnHashMap.DEFAULT_LOAD_FACTOR));
        this.loadFactor = BasicTxnHashMap.DEFAULT_LOAD_FACTOR;
        this.entrySetRef = defaultRefFactory.newTxnRef(null);
        this.keySetRef = defaultRefFactory.newTxnRef(null);
        this.valuesRef = defaultRefFactory.newTxnRef(null);

        @SuppressWarnings("unchecked")
        final TxnRef<BasicEntry<K, V>>[] entriesRef = new TxnRef[BasicTxnHashMap.DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < entriesRef.length; i++) {
            entriesRef[i] = defaultRefFactory.newTxnRef(null);
        }

        tableRef = defaultRefFactory.newTxnRef(entriesRef);
    }

    private void addEntry(final Txn txn, final int hash, final K key, final V value, final int bucketIndex) {
        final BasicEntry<K, V> entry = tableRef.get(txn)[bucketIndex].get(txn);
        tableRef.get(txn)[bucketIndex].set(new BasicEntry<K, V>(defaultRefFactory, hash, key, value, entry));
        sizeRef.increment(txn);
        if (sizeRef.get(txn) >= thresholdRef.get(txn)) {
            resize(txn, 2 * tableRef.get(txn).length);
        }
    }

    @Override
    public void clear(final Txn txn) {
        if (sizeRef.get(txn) == 0) {
            return;
        }

        final TxnRef<BasicEntry<K, V>>[] tab = tableRef.get(txn);
        for (int i = 0; i < tab.length; i++) {
            tab[i].set(txn, null);
        }
        sizeRef.set(0);
    }

    @Override
    public boolean containsKey(final Txn tx, final Object key) {
        return getEntry(tx, key) != null;
    }

    @Override
    public boolean containsValue(final Txn txn, final Object value) {
        for (final V v : values(txn)) {
            if (value.equals(v)) {
                return true;
            }
        }

        return false;
    }

    public TxnIterator<Entry<K, V>> createEntrySetIterator(final Txn txn) {
        return new ReadonlyEntrySetIterator<>(txn, defaultRefFactory, this);
    }

    public TxnIterator<K> createKeySetIterator(final Txn txn) {
        return new ReadonlyKeySetIterator<>(txn, defaultRefFactory, this);
    }

    public TxnIterator<V> createValuesIterator(final Txn txn) {
        return new ReadonlyValuesIterator<>(txn, defaultRefFactory, this);
    }

    @Override
    public TxnSet<Entry<K, V>> entrySet(final Txn txn) {
        if (entrySetRef.isNull(txn)) {
            entrySetRef.set(txn, new EntrySet<>(stm, this));
        }

        return entrySetRef.get(txn);
    }

    @Override
    public V get(final Txn txn, final Object key) {
        final BasicEntry<K, V> entry = getEntry(txn, key);
        return entry == null ? null : entry.valueRef.get(txn);
    }

    private BasicEntry<K, V> getEntry(final Txn txn, final Object key) {
        if (key == null) {
            return null;
        }

        if (sizeRef.get(txn) == 0) {
            return null;
        }

        final int hash = key.hashCode();

        for (BasicEntry<K, V> entry = tableRef.get(txn)[BasicTxnHashMap.indexFor(hash, tableRef.get(txn).length)].get(txn); entry != null; entry = entry.nextRef.get(txn)) {
            Object k;
            if (entry.hash == hash && ((k = entry.key) == key || key.equals(k))) {
                return entry;
            }
        }
        return null;
    }

    public float getLoadFactor() {
        return loadFactor;
    }

    @Override
    public TxnSet<K> keySet(final Txn txn) {
        if (keySetRef.isNull(txn)) {
            keySetRef.set(txn, new KeySet<>(stm, this));
        }

        return keySetRef.get(txn);
    }

    @Override
    public V put(final Txn txn, final K key, final V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        final int hash = key.hashCode();

        final int bucketIndex = BasicTxnHashMap.indexFor(hash, tableRef.get(txn).length);
        for (BasicEntry<K, V> entry = tableRef.get(txn)[bucketIndex].get(txn); entry != null; entry = entry.nextRef.get()) {
            Object foundKey;
            if (entry.hash == hash && ((foundKey = entry.key) == key || key.equals(foundKey))) {
                final V oldValue = entry.valueRef.get(txn);
                entry.valueRef.set(txn, value);
                return oldValue;
            }
        }

        addEntry(txn, hash, key, value, bucketIndex);
        return null;
    }

    @Override
    public V remove(final Txn tx, final Object key) {
        // TODO: implement
        throw new TodoException();
    }

    private void resize(final Txn txn, final int newCapacity) {
        final TxnRef<BasicEntry<K, V>>[] oldTable = tableRef.get(txn);
        final int oldCapacity = oldTable.length;
        if (oldCapacity == BasicTxnHashMap.MAXIMUM_CAPACITY) {
            thresholdRef.set(Integer.MAX_VALUE);
            return;
        }

        @SuppressWarnings("unchecked")
        final TxnRef<BasicEntry<K, V>>[] newTable = new TxnRef[newCapacity];
        for (int k = 0; k < newTable.length; k++) {
            newTable[k] = defaultRefFactory.newTxnRef(null);
        }

        transfer(txn, newTable);
        tableRef.set(txn, newTable);
        thresholdRef.set(txn, (int) (newCapacity * loadFactor));
    }

    @Override
    public int size(final Txn txn) {
        return sizeRef.get(txn);
    }

    @Override
    public String toString(final Txn txn) {
        final int s = sizeRef.get(txn);
        if (s == 0) {
            return "[]";
        }

        return entrySet(txn).toString(txn);
    }

    private void transfer(final Txn txn, final TxnRef<BasicEntry<K, V>>[] newTable) {
        final TxnRef<BasicEntry<K, V>>[] src = tableRef.get(txn);
        final int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            BasicEntry<K, V> entry = src[j].get(txn);
            if (entry != null) {
                src[j] = null;
                do {
                    final BasicEntry<K, V> next = entry.nextRef.get(txn);
                    final int bucketIndex = BasicTxnHashMap.indexFor(entry.hash, newCapacity);
                    entry.nextRef.set(txn, newTable[bucketIndex].get(txn));
                    newTable[bucketIndex].set(txn, entry);
                    entry = next;
                } while (entry != null);
            }
        }
    }

    @Override
    public TxnCollection<V> values(final Txn txn) {
        if (valuesRef.isNull(txn)) {
            valuesRef.set(txn, new ReadonlyValues<>(stm, this));
        }
        return valuesRef.get(txn);
    }

}
