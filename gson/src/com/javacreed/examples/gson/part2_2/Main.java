package com.javacreed.examples.gson.part2_2;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

  public static void main(final String[] args) throws Exception {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Books.class, new BooksDeserializer());
    final Gson gson = gsonBuilder.create();

    // The JSON data
    try (Reader data = new InputStreamReader(Main.class.getResourceAsStream("books.json"), "UTF-8")) {

      // Parse JSON to Java
      final Books books = gson.fromJson(data, Books.class);
      System.out.println(books);
    }
  }
}
