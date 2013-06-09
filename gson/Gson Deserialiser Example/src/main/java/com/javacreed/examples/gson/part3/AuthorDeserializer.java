package com.javacreed.examples.gson.part3;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class AuthorDeserializer implements JsonDeserializer<Author> {

  private final ThreadLocal<Map<String, Author>> authorsByName = new ThreadLocal<Map<String, Author>>() {
    @Override
    protected Map<String, Author> initialValue() {
      return new TreeMap<String, Author>(String.CASE_INSENSITIVE_ORDER);
    }
  };

  @Override
  public Author deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {

    final String name = json.getAsString();
    if (authorsByName.get().containsKey(name)) {
      return authorsByName.get().get(name);
    }

    final Author author = new Author(name);
    authorsByName.get().put(name, author);
    return author;
  }
}
