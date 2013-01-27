package com.javacreed.examples.di.part2;

public class TweetService implements MessageService {

  public void sendMessage(final String subject, final String message) {
    System.out.printf("Tweet: %s, %s%n", subject, message);
  }

}
