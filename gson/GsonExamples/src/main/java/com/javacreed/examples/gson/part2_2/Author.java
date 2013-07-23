package com.javacreed.examples.gson.part2_2;

import java.util.HashSet;
import java.util.Set;

public class Author {

  private final Set<Book> books = new HashSet<>();
  private final String name;

  public Author(final String name) {
    this.name = name;
  }

  public void addBook(final Book book) {
    books.add(book);
  }

  public Set<Book> getBooks() {
    return books;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s has %d book(s)", name, books.size());
  }

}
