package com.javacreed.examples.gson.part1;

public class Subject {

  private String title;
  private Tutor leadTutor;

  public Tutor getLeadTutor() {
    return leadTutor;
  }

  public String getTitle() {
    return title;
  }

  public void setLeadTutor(final Tutor leadTutor) {
    this.leadTutor = leadTutor;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Subject [title=" + title + ", leadTutor=" + leadTutor + "]";
  }

}
