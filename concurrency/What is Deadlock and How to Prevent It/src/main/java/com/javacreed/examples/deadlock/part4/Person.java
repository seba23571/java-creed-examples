package com.javacreed.examples.deadlock.part4;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Person {

  @GuardedBy("guardian")
  private String name;

  @GuardedBy("guardian")
  private String surname;

  private final Guardian guardian = new Guardian();

  public Person() {
  }

  public Person(final String name, final String surname) {
    this.name = name;
    this.surname = surname;
  }

  public void copyFrom(final Person person) {
    final Runnable runnable = new Runnable() {
      @Override
      public void run() {
        Person.this.name = person.name;
        Person.this.surname = person.surname;
      }
    };

    Guardian.lockAndExecute(runnable, guardian, person.guardian);
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

  @Override
  public String toString() {
    return String.format("%s %s", name, surname);
  }
}
