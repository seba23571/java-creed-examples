package com.javacreed.examples.gson.part1;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ItemServiceTest {

  private final ItemService itemService = new ItemServiceImpl();

  @Test
  public void testArray() {
    final String json = "[{'name':'Sample Text','quantity':7}]";
    final Item[] items = itemService.parseItemsArray(json);
    Assert.assertNotNull(items);
    Assert.assertEquals(1, items.length);
    final Item item = items[0];
    Assert.assertEquals("Sample Text", item.getName());
    Assert.assertEquals(7, item.getQuantity());
  }

  @Test
  public void testList() {
    final String json = "[{'name':'Sample Text','quantity':7}]";
    final List<Item> items = itemService.parseItemsList(json);
    Assert.assertNotNull(items);
    Assert.assertEquals(1, items.size());
    final Item item = items.get(0);
    Assert.assertEquals("Sample Text", item.getName());
    Assert.assertEquals(7, item.getQuantity());
  }

}
