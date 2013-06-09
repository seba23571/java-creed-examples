package com.javacreed.examples.gson.part3;

public class Person {
  private String NAME;
  private String LOCATION;
  private Exam EXAM;

  // Getters and setters are not required for this example.
  // GSON sets the fields directly using reflection.

  @Override
  public String toString() {
    return NAME + " - " + LOCATION + " (" + EXAM + ")";
  }
}
