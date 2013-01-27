package com.javacreed.examples.di.part2;

import com.google.inject.Inject;

public class Person {

  private final MessageService messageService;

  @Inject
  private Person(final MessageService messageService) {
    this.messageService = messageService;
  }

  public void greetFriend() {
    messageService.sendMessage("Hello", "Hello my friend :)");
  }
}
