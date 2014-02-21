/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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
