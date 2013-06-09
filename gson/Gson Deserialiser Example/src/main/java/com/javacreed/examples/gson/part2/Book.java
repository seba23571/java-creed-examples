package com.javacreed.examples.gson.part2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Book {

  private final Set<Author> authors;
  private final String isbn;
  private final String title;

  public Book(final String title, final String isbn, final Author... authors) {
    this.title = title;
    this.isbn = isbn;
    this.authors = new HashSet<>(Arrays.asList(authors));
  }

  public Set<Author> getAuthors() {
    return authors;
  }

  @Override
  public String toString() {
    final StringBuilder fomrattedString = new StringBuilder();
    fomrattedString.append(title).append(" (").append(isbn).append(")");

    fomrattedString.append(" by: ");
    for (final Author author : authors) {
      fomrattedString.append(author.getName()).append(", ");
    }

    // To remove the last comma followed by a space
    return fomrattedString.substring(0, fomrattedString.length() - 2);
  }
}
