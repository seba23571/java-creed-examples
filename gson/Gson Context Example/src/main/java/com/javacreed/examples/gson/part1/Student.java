package com.javacreed.examples.gson.part1;

import java.util.List;

public class Student extends Person {

  private List<Subject> subjects;

  public List<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(final List<Subject> subjects) {
    this.subjects = subjects;
  }

  @Override
  public String toString() {
    return "Student [subjects=" + subjects + "]";
  }

}
