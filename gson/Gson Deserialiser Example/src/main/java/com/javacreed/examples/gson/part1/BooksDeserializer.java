package com.javacreed.examples.gson.part1;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BooksDeserializer implements JsonDeserializer<Books> {

  @Override
  public Books deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {
    final Books books = new Books();

    final JsonObject jsonObject = json.getAsJsonObject();
    for (int i = 1; jsonObject.has(String.valueOf(i)); i++) {
      books.addBookTitle(jsonObject.get(String.valueOf(i)).getAsString());
    }

    return books;
  }
}
