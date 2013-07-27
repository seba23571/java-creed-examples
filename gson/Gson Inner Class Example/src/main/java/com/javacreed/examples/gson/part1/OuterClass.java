package com.javacreed.examples.gson.part1;

public class OuterClass {

  public class InnerClass {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "InnerClass [name=" + name + "]";
    }

  }

  private InnerClass item;

  public InnerClass getItem() {
    return item;
  }

  public void setItem(final InnerClass item) {
    this.item = item;
  }

  @Override
  public String toString() {
    return "OuterClass [item=" + item + "]";
  }

}
