package com.javacreed.examples.gson.part3;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class ItemInstanceCreator implements InstanceCreator<Item> {

  @Override
  public Item createInstance(final Type type) {
    // The fields' values will be replaced by GSON later one
    return new Item(null, null, null);
  }

}
