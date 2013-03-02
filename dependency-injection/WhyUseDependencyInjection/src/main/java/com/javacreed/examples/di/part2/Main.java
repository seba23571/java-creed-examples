package com.javacreed.examples.di.part2;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
  public static void main(final String[] args) {
    final Injector injector = Guice.createInjector(new ProjectModule());
    final Person person = injector.getInstance(Person.class);
    person.greetFriend();
  }
}
