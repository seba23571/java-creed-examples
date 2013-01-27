package com.javacreed.examples.di.part1;

public class Person {
  private final Email email = new Email();

  public void greetFriend() {
    email.sendEmail("Hello", "Hello my friend :)");
  }
}
