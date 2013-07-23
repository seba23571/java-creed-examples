package com.javacreed.examples.gson.part1;

public class OuterClass {

  public class InnerClass {
    private String name;

    @Override
    public String toString() {
      return "InnerClass [name=" + name + "]";
    }

  }

  private InnerClass item;

  @Override
  public String toString() {
    return "OuterClass [item=" + item + "]";
  }

}
