package com.javacreed.examples.gson.part2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

  public static void main(final String[] args) throws IOException {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Book.class, new BookDeserializer());
    gsonBuilder.registerTypeAdapter(Author.class, new AuthorDeserializer());
    final Gson gson = gsonBuilder.create();

    // Read the JSON data
    try (Reader data = new InputStreamReader(Main.class.getResourceAsStream("/part2/sample.json"), "UTF-8")) {

      // Parse JSON to Java
      final Book book = gson.fromJson(data, Book.class);
      System.out.println(book);
    }
  }
}
