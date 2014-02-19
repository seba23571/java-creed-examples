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
package com.javacreed.examples.collections.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person {

  public static class Builder {
    private String name;
    private final List<String> friends = new ArrayList<>();

    public Builder addFriend(final String name) {
      this.friends.add(name);
      return this;
    }

    public Person build() {
      return new Person(this);
    }

    public Builder setName(final String name) {
      this.name = name;
      return this;
    }

  }

  private final String name;
  private final List<String> friends;

  private Person(final Builder builder) {
    this.name = builder.name;
    this.friends = Collections.unmodifiableList(builder.friends);
  }

  public List<String> getFriends() {
    return friends;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s has %d friends", name, friends.size());
  }
}
