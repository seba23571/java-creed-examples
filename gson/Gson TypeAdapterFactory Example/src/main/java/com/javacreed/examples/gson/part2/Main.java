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
package com.javacreed.examples.gson.part2;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) throws IOException {
    // Configure GSON
    final DataTypeAdapterFactory.Builder dtafBuilder = new DataTypeAdapterFactory.Builder();
    dtafBuilder.add(Book.class, new BookTypeAdapter());

    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapterFactory(dtafBuilder.build());
    gsonBuilder.setPrettyPrinting();

    final Gson gson = gsonBuilder.create();

    final Book book = new Book();
    book.setAuthors(new Author[] { new Author(1, "Joshua Bloch") });
    book.setTitle("Effective Java");
    book.setIsbn("978-0321356680");

    final String json = gson.toJson(book);
    System.out.println(json);
  }
}
