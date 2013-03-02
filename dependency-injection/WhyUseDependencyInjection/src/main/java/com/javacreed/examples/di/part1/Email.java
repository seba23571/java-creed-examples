package com.javacreed.examples.di.part1;

public class Email {
  public void sendEmail(final String subject, final String message) {
    System.out.printf("Email: %s, %s%n ", subject, message);
  }
}
