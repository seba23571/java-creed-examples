package com.javacreed.examples.gson;

public class Item {
  private String name;
  private int quantity;

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Item [name=" + name + ", quantity=" + quantity + "]";
  }

}
