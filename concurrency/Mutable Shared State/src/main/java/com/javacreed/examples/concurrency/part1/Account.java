package com.javacreed.examples.concurrency.part1;

public class Account {

  private int balance;

  public Account(final int balance) {
    this.balance = balance;
  }

  public void adjustBy(final int amount) {
    balance -= amount;
  }

  @Override
  public String toString() {
    return String.format("Balance: %d", balance);
  }
}
