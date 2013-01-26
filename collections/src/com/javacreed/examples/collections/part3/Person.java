package com.javacreed.examples.collections.part3;

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
    this.friends = Collections.unmodifiableList(new ArrayList<>(builder.friends));
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
