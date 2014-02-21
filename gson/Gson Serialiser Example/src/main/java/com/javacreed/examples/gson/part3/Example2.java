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

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Example2 {

  public static void main(final String[] args) throws IOException {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Book.class, new BookSerialiser());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();

    final Author joshuaBloch = new Author();
    joshuaBloch.setId(1);
    joshuaBloch.setName("Joshua Bloch");

    final Author nealGafter = new Author();
    nealGafter.setId(2);
    nealGafter.setName("Neal Gafter");

    final Book javaPuzzlers = new Book();
    javaPuzzlers.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");
    javaPuzzlers.setIsbn("032133678X");
    javaPuzzlers.setAuthors(new Author[] { joshuaBloch, nealGafter });

    final Book effectiveJava = new Book();
    effectiveJava.setTitle("Effective Java (2nd Edition)");
    effectiveJava.setIsbn("0321356683");
    effectiveJava.setAuthors(new Author[] { joshuaBloch });

    final Book[] books = new Book[] { javaPuzzlers, effectiveJava };

    final String json = gson.toJson(books);
    System.out.println(json);
  }
}
