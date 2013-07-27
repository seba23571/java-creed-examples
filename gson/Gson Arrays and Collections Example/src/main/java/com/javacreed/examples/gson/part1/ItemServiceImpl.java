package com.javacreed.examples.gson.part1;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ItemServiceImpl implements ItemService {

  private final Gson gson = new GsonBuilder().create();

  @Override
  public Item[] parseItemsArray(final String json) {
    return gson.fromJson(json, Item[].class);
  }

  @Override
  public List<Item> parseItemsList(final String json) {
    /* This is the only way we can specify the return type as list of this type (item) */
    final Type collectionType = new TypeToken<List<Item>>() {}.getType();
    return gson.fromJson(json, collectionType);
  }
}
