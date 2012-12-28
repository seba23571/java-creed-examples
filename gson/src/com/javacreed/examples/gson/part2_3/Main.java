package com.javacreed.examples.gson.part2_3;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) throws Exception {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Id.class, new IdInstanceCreator<Name>(Name.class));
    gsonBuilder.registerTypeAdapter(Name.class, new NameInstanceCreator());
    final Gson gson = gsonBuilder.create();

    // The JSON data
    try (Reader data = new InputStreamReader(Main.class.getResourceAsStream("names.json"), "UTF-8")) {

      // Parse JSON to Java
      final Name name = gson.fromJson(data, Name.class);
      System.out.println(name);
    }
  }
}
