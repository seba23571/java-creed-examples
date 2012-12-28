package com.javacreed.examples.gson.part2_4;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProductJsonSerializer implements JsonSerializer<Product> {

  @Override
  public JsonElement serialize(final Product product, final Type typeOfSrc, final JsonSerializationContext context) {

    final JsonObject json = new JsonObject();
    json.addProperty("i", product.getId());
    json.addProperty("n", product.getName());
    json.addProperty("p", product.getPrice());

    final JsonArray categoriesArray = new JsonArray();
    json.add("c", categoriesArray);
    for (final Category category : product.getCategories()) {
      categoriesArray.add(context.serialize(category));
    }

    return json;
  }

}
