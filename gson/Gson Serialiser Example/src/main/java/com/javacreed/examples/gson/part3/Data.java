package com.javacreed.examples.gson.part3;

public class Data {

  private Author[] authors;
  private Book[] books;

  public Author[] getAuthors() {
    return authors;
  }

  public Book[] getBooks() {
    return books;
  }

  public void setAuthors(final Author[] authors) {
    this.authors = authors;
  }

  public void setBooks(final Book[] books) {
    this.books = books;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    formatted.append("Authors: ");
    for (final Author author : authors) {
      formatted.append("\n").append(author);
    }

    formatted.append("\n\nBooks");
    for (final Book book : books) {
      formatted.append("\n").append(book);
    }

    return formatted.toString();
  }

}
