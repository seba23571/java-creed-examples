package com.javacreed.examples.spring.core.domain;

public class Customer {

  private String name;
  private String streetAdress;
  private String city;
  private String postalCode;

  public String getCity() {
    return city;
  }

  public String getName() {
    return name;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getStreetAdress() {
    return streetAdress;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public void setStreetAdress(final String streetAdress) {
    this.streetAdress = streetAdress;
  }

}
