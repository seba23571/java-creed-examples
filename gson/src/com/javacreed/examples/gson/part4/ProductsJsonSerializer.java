package com.javacreed.examples.gson.part4;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProductsJsonSerializer implements JsonSerializer<Collection<Product>> {

  @Override
  public JsonElement serialize(final Collection<Product> products, final Type typeOfSrc,
      final JsonSerializationContext context) {

    final JsonObject json = new JsonObject();
    json.addProperty("size", products.size());
    
    final JsonArray jsonArray = new JsonArray();
    json.add("collection", jsonArray);
    for (final Product product : products) {
      final JsonElement element = context.serialize(product);
      jsonArray.add(element);
    }

    return json;
  }

}
