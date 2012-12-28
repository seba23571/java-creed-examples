package com.javacreed.examples.gson.part3;

public class Name {

  private final Id<Name> nameId;
  private final String firstName;
  private final String lastName;

  public Name(final Id<Name> nameId, final String firstName, final String lastName) {
    this.nameId = nameId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Id<Name> getNameId() {
    return nameId;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s %s", nameId, firstName, lastName);
  }

}
