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
