package com.javacreed.examples.concurrency.part3;

import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

import static org.multiverse.api.StmUtils.*;


public class StmAccount {

    private final TxnLong lastUpdate;
    private final TxnInteger balance;

    public StmAccount(int balance) {
        this.lastUpdate = newTxnLong(System.currentTimeMillis());
        this.balance = newTxnInteger(balance);
    }

    public long getLastUpdate() {
        return lastUpdate.get();
    }

    public int getBalance() {
        return balance.get();
    }

    @Override
    public String toString() {
        final String[] formatted = new String[1];
        atomic(new Runnable() {
            @Override
            public void run() {
                formatted[0] = String.format("%d (as of %tF %<tT)", balance.get(), lastUpdate.get());
            }
        });
        return formatted[0];
    }
}
