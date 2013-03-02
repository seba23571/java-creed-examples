package com.javacreed.examples.gson.part3;

import java.math.BigDecimal;

public class Item {

  private final Id<Item> id;
  private final String name;
  private final BigDecimal price;

  public Item(final Id<Item> id, final String name, final BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Id<Item> getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return String.format("[%s] %s %.2f", id, name, price);
  }

}
