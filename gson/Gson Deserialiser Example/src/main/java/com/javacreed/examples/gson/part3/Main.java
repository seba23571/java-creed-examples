package com.javacreed.examples.gson.part3;

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
    try (Reader reader = new InputStreamReader(Main.class.getResourceAsStream("/part3/sample.json"), "UTF-8")) {

      // Parse JSON to Java
      final Data data = gson.fromJson(reader, Data.class);
      System.out.println(data);
    }
  }
}
