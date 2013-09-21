package com.javacreed.examples.gson.part1;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(String[] args) throws Exception {
    // Configure GSON
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Books.class, new BooksDeserializer());
    Gson gson = gsonBuilder.create();

    // The JSON data
    Reader data = new InputStreamReader(Main.class.getResourceAsStream("/books1.json"), "UTF-8");

    // Parse JSON to Java
    Books books = gson.fromJson(data, Books.class);
    System.out.println(books);
  }
}
