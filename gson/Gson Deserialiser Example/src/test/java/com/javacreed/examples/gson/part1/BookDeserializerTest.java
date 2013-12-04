package com.javacreed.examples.gson.part1;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BookDeserializerTest {

  @Test
  public void test() {
    final String json =//@formatter:off
        "{'title': 'Java Puzzlers: Traps, Pitfalls, and Corner Cases'," +
        "'isbn-10': '032133678X'," +
        "'isbn-13': '978-0321336781'," +
        "'authors': ['Joshua Bloch', 'Neal Gafter']}";//@formatter:on

    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Book.class, new BookDeserializer());
    final Gson gson = gsonBuilder.create();

    // Test the deserialiser
    final Book book = gson.fromJson(json, Book.class);
    Assert.assertNotNull(book);
    Assert.assertEquals("Java Puzzlers: Traps, Pitfalls, and Corner Cases", book.getTitle());
    Assert.assertEquals("032133678X", book.getIsbn10());
    Assert.assertEquals("978-0321336781", book.getIsbn13());
    Assert.assertArrayEquals(new String[] { "Joshua Bloch", "Neal Gafter" }, book.getAuthors());
  }

}
