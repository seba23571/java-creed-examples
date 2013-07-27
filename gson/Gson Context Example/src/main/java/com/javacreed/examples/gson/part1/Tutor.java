package com.javacreed.examples.gson.part1;

public class Tutor extends Person {

  private Subject subject;

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  @Override
  public String toString() {
    return "Tutor [subject=" + subject + "]";
  }

}
