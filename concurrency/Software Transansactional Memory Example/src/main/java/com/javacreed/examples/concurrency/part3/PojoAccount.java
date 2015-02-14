package com.javacreed.examples.concurrency.part3;

public class PojoAccount {

    private final long lastUpdate;
    private final int balance;

    public PojoAccount(int balance) {
        this.lastUpdate = System.currentTimeMillis();
        this.balance = balance;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("%d (as of %tF %<tT)", balance, lastUpdate);
    }
}
