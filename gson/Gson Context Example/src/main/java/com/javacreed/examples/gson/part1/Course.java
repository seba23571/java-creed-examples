package com.javacreed.examples.gson.part1;

import java.util.List;

public class Course {

  private Subject subject;
  private Address address;
  private Tutor tutor;
  private List<Student> sudents;

  public Address getAddress() {
    return address;
  }

  public Subject getSubject() {
    return subject;
  }

  public List<Student> getSudents() {
    return sudents;
  }

  public Tutor getTutor() {
    return tutor;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  public void setSudents(final List<Student> sudents) {
    this.sudents = sudents;
  }

  public void setTutor(final Tutor tutor) {
    this.tutor = tutor;
  }

  @Override
  public String toString() {
    return "Course [subject=" + subject + ", address=" + address + ", tutor=" + tutor + ", sudents=" + sudents + "]";
  }

}
