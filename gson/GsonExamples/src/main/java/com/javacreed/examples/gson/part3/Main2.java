package com.javacreed.examples.gson.part3;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main2 {
  public static void main(final String[] args) throws Exception {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Id.class, new IdInstanceCreator<Name>(Name.class));
    final Gson gson = gsonBuilder.create();

    // The JSON data
    try (Reader data = new InputStreamReader(Main2.class.getResourceAsStream("/part3/ids.json"), "UTF-8")) {

      // Parse JSON to Java
      @SuppressWarnings("unchecked")
      final Id<Name> idName = gson.fromJson(data, Id.class);
      System.out.println(idName);
    }
  }
}
