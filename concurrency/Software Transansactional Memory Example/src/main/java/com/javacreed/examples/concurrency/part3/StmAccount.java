package com.javacreed.examples.concurrency.part3;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class StmAccount {

  private final TxnLong lastUpdate;
  private final TxnInteger balance;

  public StmAccount(final int balance) {
    this.lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public int getBalance() {
    return balance.get();
  }

  public long getLastUpdate() {
    return lastUpdate.get();
  }

  @Override
  public String toString() {
    final String[] formatted = new String[1];
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        formatted[0] = String.format("%d (as of %tF %<tT)", balance.get(), lastUpdate.get());
      }
    });
    return formatted[0];
  }
}
