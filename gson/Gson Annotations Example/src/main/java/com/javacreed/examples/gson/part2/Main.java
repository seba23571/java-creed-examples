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

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) throws Exception {
    final GsonBuilder builder = new GsonBuilder();
    builder.excludeFieldsWithoutExposeAnnotation();
    final Gson gson = builder.create();

    final Account account = new Account();
    account.setAccountNumber("A123 45678 90");
    account.setIban("IBAN 11 22 33 44");
    account.setOwner("Albert Attard");
    account.setPin("1234");
    account.setAddress("Somewhere, Far Far Away");

    final String json = gson.toJson(account);
    System.out.printf("Serialised%n  %s%n", json);

    try (final Reader data = new InputStreamReader(Main.class.getResourceAsStream("/part2/account.json"), "UTF-8")) {

      final Account otherAccount = gson.fromJson(data, Account.class);
      System.out.println("Deserialised");
      System.out.printf("  Account Number: %s%n", otherAccount.getAccountNumber());
      System.out.printf("  IBAN:           %s%n", otherAccount.getIban());
      System.out.printf("  Owner:          %s%n", otherAccount.getOwner());
      System.out.printf("  Pin:            %s%n", otherAccount.getPin());
      System.out.printf("  Address:        %s%n", otherAccount.getAddress());
    }
  }
}
