package com.javacreed.examples.gson.part2_3;

public class Main1 {
  public static void main(final String[] args) throws Exception {
    final Id<Name> id = new Id<>(Name.class, 1);
    final Name name = new Name(id, "Albert", "Attard");
    System.out.println(name);
  }
}
