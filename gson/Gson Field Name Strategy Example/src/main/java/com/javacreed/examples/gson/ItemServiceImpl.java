package com.javacreed.examples.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ItemServiceImpl implements ItemService {

  private final Gson gson = new GsonBuilder().setFieldNamingStrategy(new ItemFieldNameStrategy()).create();

  @Override
  public Item parseItem(final String json) {
    return gson.fromJson(json, Item.class);
  }

}
