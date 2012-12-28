package com.javacreed.examples.gson.part2_4;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Product {

  private int id;
  private String name;
  private BigDecimal price;
  private Set<Category> categories;

  public void addCategory(final Category category) {
    if (categories == null) {
      categories = new HashSet<>();
    }

    categories.add(category);
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setCategories(final Set<Category> categories) {
    this.categories = categories;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("%s at %.2f", name, price);
  }

}
