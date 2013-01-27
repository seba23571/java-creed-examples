package com.javacreed.examples.di.part2;

public class EmailService implements MessageService {

  public void sendMessage(final String subject, final String message) {
    System.out.printf("Email: %s, %s%n ", subject, message);
  }
}
