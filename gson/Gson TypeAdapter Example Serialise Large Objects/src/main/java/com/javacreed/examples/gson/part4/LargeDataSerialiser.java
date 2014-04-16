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
package com.javacreed.examples.gson.part4;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LargeDataSerialiser implements JsonSerializer<LargeData> {

  @Override
  public JsonElement serialize(final LargeData data, final Type typeOfSrc, final JsonSerializationContext context) {
    final JsonArray jsonNumbers = new JsonArray();
    for (final long number : data.getNumbers()) {
      jsonNumbers.add(new JsonPrimitive(number));
    }

    final JsonObject jsonObject = new JsonObject();
    jsonObject.add("numbers", jsonNumbers);
    return jsonObject;
  }
}
