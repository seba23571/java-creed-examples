package com.javacreed.examples.di.part4;

import com.google.inject.Inject;

public class Person {

  private final MessageService messageService;
  private final WritingService writingService;

  @Inject
  private Person(final MessageService messageService, WritingService writeService) {
    this.messageService = messageService;
    this.writingService = writeService;
  }

  public void greetFriend() {
    messageService.sendMessage("Hello", "Hello my friend :)");
  }
  
  public void writeToFriend() {
    writingService.write("Hello my friend :)");
  }

}
