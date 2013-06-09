package com.javacreed.examples.gson.part1;

public class Subject {

  private String title;
  private Tutor leadTutor;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Tutor getLeadTutor() {
    return leadTutor;
  }

  public void setLeadTutor(Tutor leadTutor) {
    this.leadTutor = leadTutor;
  }

  @Override
  public String toString() {
    return "Subject [title=" + title + ", leadTutor=" + leadTutor + "]";
  }

}
