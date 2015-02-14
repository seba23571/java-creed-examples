package com.javacreed.examples.concurrency.part1;

import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

import static org.multiverse.api.StmUtils.*;


public class Account {

    private final TxnLong lastUpdate;
    private final TxnInteger balance;

    public Account(int balance) {
        this.lastUpdate = newTxnLong(System.currentTimeMillis());
        this.balance = newTxnInteger(balance);
    }

    public void adjustBy(final int amount) {
        adjustBy(amount, System.currentTimeMillis());
    }

    public void adjustBy(final int amount, final long date) {
        atomic(new Runnable() {
            public void run() {
                balance.increment(amount);
                lastUpdate.set(date);

                if (balance.get() < 0) {
                    throw new IllegalStateException("Not enough money");
                }
            }
        });
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
