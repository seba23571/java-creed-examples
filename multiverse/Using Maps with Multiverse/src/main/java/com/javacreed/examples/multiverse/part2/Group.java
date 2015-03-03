package com.javacreed.examples.multiverse.part2;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
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

    @Override
    public String toString() {
        return StmUtils.atomic(new TxnCallable<String>() {
            @Override
            public String call(final Txn txn) throws Exception {
                return members.toString(txn);
            }
        });
    }
}
