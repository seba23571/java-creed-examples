package com.javacreed.examples.gson.part2_4;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CategoryJsonSerializer implements JsonSerializer<Category> {

  @Override
  public JsonElement serialize(final Category category, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(category.getCode());
  }

}
