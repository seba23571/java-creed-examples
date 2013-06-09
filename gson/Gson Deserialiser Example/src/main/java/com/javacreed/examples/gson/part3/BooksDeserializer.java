package com.javacreed.examples.gson.part3;

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
    final JsonObject jsonObject = json.getAsJsonObject();

    final Books books = new Books();
    books.addBook((Book) context.deserialize(jsonObject.get("1"), Book.class));
    books.addBook((Book) context.deserialize(jsonObject.get("2"), Book.class));
    books.addBook((Book) context.deserialize(jsonObject.get("3"), Book.class));
    books.addBook((Book) context.deserialize(jsonObject.get("4"), Book.class));

    return books;
  }
}
