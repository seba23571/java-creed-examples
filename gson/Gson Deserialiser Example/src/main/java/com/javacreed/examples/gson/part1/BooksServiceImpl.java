package com.javacreed.examples.gson.part1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BooksServiceImpl implements BooksService {

  private final Gson gson;

  public BooksServiceImpl() {
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Books.class, new BooksDeserializer());
    gson = gsonBuilder.create();
  }

  @Override
  public Books parseBooks(final String json) {
    return gson.fromJson(json, Books.class);
  }

}
