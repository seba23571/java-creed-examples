package com.javacreed.examples.di.part3;

public class MockMessageService implements MessageService {

  public String subject;
  public String message;

  public void sendMessage(final String subject, final String message) {
    this.subject = subject;
    this.message = message;
  }

}
