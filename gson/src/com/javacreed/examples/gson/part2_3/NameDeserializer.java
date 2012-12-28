package com.javacreed.examples.gson.part2_3;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NameDeserializer implements JsonDeserializer<Name> {

  @Override
  public Name deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {

    final JsonObject object = json.getAsJsonObject();

    final int idValue = object.get("id").getAsInt();
    final String firstName = object.get("firstName").getAsString();
    final String lastName = object.get("lastName").getAsString();

    final Id<Name> id = new Id<>(Name.class, idValue);
    final Name name = new Name(id, firstName, lastName);
    return name;
  }

}
