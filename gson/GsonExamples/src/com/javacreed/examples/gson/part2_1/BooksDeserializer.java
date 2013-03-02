package com.javacreed.examples.gson.part2_1;

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
    books.addBookTitle(jsonObject.get("1").getAsString());
    books.addBookTitle(jsonObject.get("2").getAsString());
    books.addBookTitle(jsonObject.get("3").getAsString());
    books.addBookTitle(jsonObject.get("4").getAsString());

    return books;
  }
}
