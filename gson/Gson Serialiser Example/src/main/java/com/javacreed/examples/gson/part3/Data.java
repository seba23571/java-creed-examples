/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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
