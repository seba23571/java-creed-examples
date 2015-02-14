package com.javacreed.examples.concurrency.part3;

public class PojoAccount {

  private final long lastUpdate;
  private final int balance;

  public PojoAccount(final int balance) {
    this.lastUpdate = System.currentTimeMillis();
    this.balance = balance;
  }

  public int getBalance() {
    return balance;
  }

  public long getLastUpdate() {
    return lastUpdate;
  }

  @Override
  public String toString() {
    return String.format("%d (as of %tF %<tT)", balance, lastUpdate);
  }
}
