package com.javacreed.examples.gson.part2_3;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class NameInstanceCreator implements InstanceCreator<Name> {

  @Override
  public Name createInstance(final Type type) {
    // The fields' values will be replaced by GSON later one
    return new Name(Id.createEmptyInstance(Name.class), null, null);
  }

}
