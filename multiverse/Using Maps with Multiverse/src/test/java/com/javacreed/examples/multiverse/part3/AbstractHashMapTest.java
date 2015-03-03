package com.javacreed.examples.multiverse.part3;

import org.multiverse.api.GlobalStmInstance;
import org.multiverse.api.Stm;
import org.multiverse.api.TxnThreadLocal;
import org.multiverse.api.collections.TxnMap;

public abstract class AbstractHashMapTest {

    protected <K, V> TxnMap<K, V> createMap() {
        final Stm stm = GlobalStmInstance.getGlobalStmInstance();
        TxnThreadLocal.clearThreadLocalTxn();

        return new BasicTxnHashMap<>(stm);
    }
}
