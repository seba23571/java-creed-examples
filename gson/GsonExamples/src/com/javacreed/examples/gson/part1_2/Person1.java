package com.javacreed.examples.gson.part1_2;

public class Person1 {
  private String NAME;
  private String LOCATION;

  // Getters and setters are not required for this example.
  // GSON sets the fields directly.

  @Override
  public String toString() {
    return NAME + " - " + LOCATION;
  }
}
