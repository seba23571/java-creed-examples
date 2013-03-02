package com.javacreed.examples.di.part2;

public class FastEmailService implements MessageService {

  public void sendMessage(final String subject, final String message) {
    System.out.printf("Fast Email: %s, %s%n ", subject, message);
  }

}
