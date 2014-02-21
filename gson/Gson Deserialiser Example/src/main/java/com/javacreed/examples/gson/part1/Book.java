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
package com.javacreed.examples.gson.part1;

public class Book {

  private String[] authors;
  private String isbn10;
  private String isbn13;
  private String title;

  public String[] getAuthors() {
    return authors;
  }

  public String getIsbn10() {
    return isbn10;
  }

  public String getIsbn13() {
    return isbn13;
  }

  public String getTitle() {
    return title;
  }

  public void setAuthors(final String[] authors) {
    this.authors = authors;
  }

  public void setIsbn10(final String isbn10) {
    this.isbn10 = isbn10;
  }

  public void setIsbn13(final String isbn13) {
    this.isbn13 = isbn13;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    formatted.append(title);
    if (isbn10 != null) {
      formatted.append(" [ISBN-10: ").append(isbn10).append("]");
    }
    if (isbn13 != null) {
      formatted.append(" [ISBN 13: ").append(isbn13).append("]");
    }
    formatted.append("\nWritten by:");
    for (final String author : authors) {
      formatted.append("\n  >> ").append(author);
    }

    return formatted.toString();
  }
}
