package com.javacreed.examples.gson.part2_4;

public enum Category {

  FRUIT("FRT", "Fruit"), VEGETABLES("VEG", "Vegetables");

  private final String code;
  private final String name;

  private Category(final String code, final String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
