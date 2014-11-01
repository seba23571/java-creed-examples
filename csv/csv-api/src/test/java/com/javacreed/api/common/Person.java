/*
 * #%L
 * JavaCreed CSV API
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.api.common;

public class Person {

  private String name;
  private String surname;
  private int age;

  public Person() {}

  public Person(final String name, final String surname, final int age) {
    this.name = name;
    this.surname = surname;
    this.age = age;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    final Person other = (Person) object;
    if (age != other.age) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (surname == null) {
      if (other.surname != null) {
        return false;
      }
    } else if (!surname.equals(other.surname)) {
      return false;
    }
    return true;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + age;
    result = prime * result + (name == null ? 0 : name.hashCode());
    result = prime * result + (surname == null ? 0 : surname.hashCode());
    return result;
  }

  public void setAge(final int age) {
    this.age = age;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  @Override
  public String toString() {
    return String.format("%s %s (%d)", name, surname, age);
  }
}
