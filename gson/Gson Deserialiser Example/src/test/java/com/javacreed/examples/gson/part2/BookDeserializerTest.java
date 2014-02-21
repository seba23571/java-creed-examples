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

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BookDeserializerTest {

  @Test
  public void test() {
    final String json =//@formatter:off
        "{ 'title': 'Java Puzzlers: Traps, Pitfalls, and Corner Cases', " +
        "  'isbn': '032133678X', " +
        "  'authors':[" +
        "    {'id': 1, " +
        "     'name': 'Joshua Bloch' }, " +
        "    {'id': 2, " +
        "     'name': 'Neal Gafter'}" +
        "  ]" +
        "}";//@formatter:on

    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Book.class, new BookDeserializer());
    final Gson gson = gsonBuilder.create();

    // Test the deserialiser
    final Book book = gson.fromJson(json, Book.class);
    Assert.assertNotNull(book);
    Assert.assertEquals("Java Puzzlers: Traps, Pitfalls, and Corner Cases", book.getTitle());
    Assert.assertEquals("032133678X", book.getIsbn());

    final Author[] authors = book.getAuthors();
    Assert.assertEquals(2, authors.length);
    Assert.assertEquals(1, authors[0].getId());
    Assert.assertEquals("Joshua Bloch", authors[0].getName());
    Assert.assertEquals(2, authors[1].getId());
    Assert.assertEquals("Neal Gafter", authors[1].getName());
  }

}
