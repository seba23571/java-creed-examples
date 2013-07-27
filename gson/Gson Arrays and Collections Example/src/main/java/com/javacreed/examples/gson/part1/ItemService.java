package com.javacreed.examples.gson.part1;

import java.util.List;

public interface ItemService {

  Item[] parseItemsArray(String json);

  List<Item> parseItemsList(String json);

}
