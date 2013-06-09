package com.javacreed.examples.gson.part3;

public class Exam {
  private String SUBJECT;
  private double GRADE;

  // Getters and setters are not required for this example.
  // GSON sets the fields directly using reflection.

  @Override
  public String toString() {
    return SUBJECT + " - " + GRADE;
  }
}
