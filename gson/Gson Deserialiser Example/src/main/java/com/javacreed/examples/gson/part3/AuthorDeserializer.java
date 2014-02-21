/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
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