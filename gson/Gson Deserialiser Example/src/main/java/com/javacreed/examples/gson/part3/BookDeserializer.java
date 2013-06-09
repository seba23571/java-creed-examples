package com.javacreed.examples.gson.part3;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BookDeserializer implements JsonDeserializer<Book> {

  @Override
  public Book deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {

    final JsonObject jsonObject = json.getAsJsonObject();
    final String title = jsonObject.get("title").getAsString();
    final String isbn = jsonObject.get("isbn").getAsString();
    final Author[] authors = context.deserialize(jsonObject.get("authors"), Author[].class);

    final Book book = new Book(title, isbn, authors);
    for (final Author author : authors) {
      author.addBook(book);
    }

    return book;
  }
}
