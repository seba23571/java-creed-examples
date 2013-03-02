package com.javacreed.examples.di.part4;

public class PenWritingService implements WritingService {

  public void write(String message) {
    System.out.printf("Pen: %s%n ", message);
  }
}
