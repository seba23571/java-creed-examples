package com.javacreed.examples.gson.part1_3;

public class Person2 {
  private String NAME;
  private String LOCATION;
  private Exam1 EXAM;

  // Getters and setters are not required for this example.
  // GSON sets the fields directly.

  @Override
  public String toString() {
    return NAME + " - " + LOCATION + " (" + EXAM + ")";
  }
}
