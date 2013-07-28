package com.javacreed.examples.gson;

import org.junit.Assert;
import org.junit.Test;

public class ItemServiceTest {

  private final ItemService itemService = new ItemServiceImpl();

  @Test
  public void test() {
    final String json = "{'item-name':'Sample Text','item-quantity':7}";
    final Item item = itemService.parseItem(json);
    Assert.assertNotNull(item);
    Assert.assertEquals("Sample Text", item.getName());
    Assert.assertEquals(7, item.getQuantity());
  }
}
