package com.javacreed.examples.gson.part2;

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

    // Delegate the deserialisation to the AuthorDeserializer class
    final Author[] authors = context.deserialize(jsonObject.get("authors"), Author[].class);

    final Book book = new Book();
    book.setTitle(jsonObject.get("title").getAsString());
    book.setIsbn(jsonObject.get("isbn").getAsString());
    book.setAuthors(authors);
    return book;
  }
}
