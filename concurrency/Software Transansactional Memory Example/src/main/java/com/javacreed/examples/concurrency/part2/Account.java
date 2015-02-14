package com.javacreed.examples.concurrency.part2;

import org.multiverse.api.StmUtils;
import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnLong;

public class Account {

  private final TxnLong lastUpdate;
  private final TxnInteger balance;

  public Account(final int balance) {
    this.lastUpdate = StmUtils.newTxnLong(System.currentTimeMillis());
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public void adjustBy(final int amount) {
    adjustBy(amount, System.currentTimeMillis());
  }

  public void adjustBy(final int amount, final long date) {
    StmUtils.atomic(new Runnable() {
      @Override
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
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        formatted[0] = String.format("%d (as of %tF %<tT)", balance.get(), lastUpdate.get());
      }
    });
    return formatted[0];
  }

  public void transferBetween(final Account other, final int amount) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        final long date = System.currentTimeMillis();
        adjustBy(-amount, date);
        other.adjustBy(amount, date);
      }
    });
  }
}
