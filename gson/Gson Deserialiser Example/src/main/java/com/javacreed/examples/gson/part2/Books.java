package com.javacreed.examples.gson.part2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Books {

  private final Map<String, Author> authors = new HashMap<>();
  private final Set<Book> books = new HashSet<>();

  public void addAuthor(final Author author) {
    authors.put(author.getName(), author);
  }

  public void addBook(final Book book) {
    books.add(book);
  }

  public Author getAuthorWithName(final String name) {
    return authors.get(name);
  }

  @Override
  public String toString() {
    final StringBuilder formattedString = new StringBuilder();
    for (final Author author : authors.values()) {
      formattedString.append(author).append("\n");
      for (final Book book : author.getBooks()) {
        formattedString.append("  ").append(book).append("\n");
      }
      formattedString.append("\n");
    }

    return formattedString.toString();
  }
}
