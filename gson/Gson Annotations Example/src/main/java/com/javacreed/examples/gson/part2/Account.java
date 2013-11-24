package com.javacreed.examples.gson.part2;

import com.google.gson.annotations.Expose;

public class Account {

  @Expose(deserialize = false)
  private String accountNumber;

  @Expose
  private String iban;

  @Expose(serialize = false)
  private String owner;

  @Expose(serialize = false, deserialize = false)
  private String address;

  private String pin;

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAddress() {
    return address;
  }

  public String getIban() {
    return iban;
  }

  public String getOwner() {
    return owner;
  }

  public String getPin() {
    return pin;
  }

  public void setAccountNumber(final String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public void setIban(final String iban) {
    this.iban = iban;
  }

  public void setOwner(final String owner) {
    this.owner = owner;
  }

  public void setPin(final String pin) {
    this.pin = pin;
  }

}
