package com.javacreed.examples.gson.part3;

public class Author {

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("[%d] %s", id, name);
  }
}
