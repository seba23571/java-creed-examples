package com.javacreed.examples.collections.part2;

public class Example {
  public static void main(final String[] args) {

    final Person.Builder builder = new Person.Builder();
    builder.setName("Albert Attard");
    builder.addFriend("John White");
    builder.addFriend("Mary Vella");

    final Person person = builder.build();
    System.out.println("Before modification: " + person);

    // Adding a new friend after the object was created
    builder.addFriend("Joe Borg");
    System.out.println("After modification: " + person);
  }
}
