package com.javacreed.examples.deadlock.part3;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Person {

  @GuardedBy("this")
  private String name;

  @GuardedBy("this")
  private String surname;

  public Person() {
  }

  public Person(final String name, final String surname) {
    this.name = name;
    this.surname = surname;
  }

  /**
   * This method is not prone to deadlock and is thread safe too
   * 
   * @param person
   */
  public void betterVersionOfCopyFrom(final Person person) {
    final String name;
    final String surname;

    synchronized (person) {
      name = person.name;
      surname = person.surname;
    }

    synchronized (this) {
      this.name = name;
      this.surname = surname;
    }
  }

  /**
   * This method is prone to deadlock, especially if used by the {@link Example1} class with the
   * {@code ThreadUtils.silentSleep(100);} uncommented.
   * 
   * @param person
   * @deprecated <strong>DO NOT USE THIS METHOD</strong> as it is prone to deadlock. Use the method:
   *             {@link #betterVersionOfCopyFrom(Person)} instead
   */
  @Deprecated
  public void copyFrom(final Person person) {
    synchronized (this) {
      // By enabling this, we will force a deadlock when Example1 is executed.
      // ThreadUtils.silentSleep(100);
      synchronized (person) {
        this.name = person.name;
        this.surname = person.surname;
      }
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

  @Override
  public String toString() {
    return String.format("%s %s", name, surname);
  }
}
