package com.javacreed.examples.gson.part3;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class AuthorDeserializer implements JsonDeserializer<Author> {

  private final ThreadLocal<Map<Integer, Author>> cache = new ThreadLocal<Map<Integer, Author>>() {
    @Override
    protected Map<Integer, Author> initialValue() {
      return new HashMap<>();
    }
  };

  @Override
  public Author deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {

    // Only the ID is available
    if (json.isJsonPrimitive()) {
      final JsonPrimitive primitive = json.getAsJsonPrimitive();
      return getOrCreate(primitive.getAsInt());
    }

    // The whole object is available
    if (json.isJsonObject()) {
      final JsonObject jsonObject = json.getAsJsonObject();

      final Author author = getOrCreate(jsonObject.get("id").getAsInt());
      author.setName(jsonObject.get("name").getAsString());
      return author;
    }

    throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
  }

  private Author getOrCreate(final int id) {
    Author author = cache.get().get(id);
    if (author == null) {
      author = new Author();
      cache.get().put(id, author);
    }
    return author;
  }
}