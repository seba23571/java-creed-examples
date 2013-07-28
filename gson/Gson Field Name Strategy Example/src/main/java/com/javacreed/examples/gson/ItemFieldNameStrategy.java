package com.javacreed.examples.gson;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;

public class ItemFieldNameStrategy implements FieldNamingStrategy {

  @Override
  public String translateName(final Field f) {
    return "item-" + f.getName();
  }
}
