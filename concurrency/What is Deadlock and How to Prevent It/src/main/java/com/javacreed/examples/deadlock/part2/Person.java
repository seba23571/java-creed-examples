package com.javacreed.examples.deadlock.part2;

public class Person {

  private String name;
  private String surname;

  /**
   * This method is prone to deadlock
   * 
   * @param person
   */
  public synchronized void copyFrom(final Person person) {
    synchronized (person) {
      this.name = person.name;
      this.surname = person.surname;
    }
  }

  public synchronized String getName() {
    return name;
  }

  public synchronized String getSurname() {
    return surname;
  }

  public synchronized void setName(final String name) {
    this.name = name;
  }

  public synchronized void setSurname(final String surname) {
    this.surname = surname;
  }
}
