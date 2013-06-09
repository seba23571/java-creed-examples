package com.javacreed.examples.gson.part1;

public class Person {
  private String name;
  private Address address;

  public Address getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Person [name=" + name + ", address=" + address + "]";
  }

}
