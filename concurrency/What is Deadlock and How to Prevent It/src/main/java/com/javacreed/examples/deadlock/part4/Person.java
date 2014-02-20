/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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
