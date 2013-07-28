package com.javacreed.examples.gson.part2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BooksServiceImpl implements BooksService {

  private final Gson gson;

  public BooksServiceImpl() {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Books.class, new BooksDeserializer());
    gson = gsonBuilder.create();
  }

  @Override
  public Books parseBooks(final String json) {
    // Parse JSON to Java
    return gson.fromJson(json, Books.class);
  }
}
