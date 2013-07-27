package com.javacreed.examples.gson;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

  private final DataService dataService = new DataServiceImpl();

  @Test
  public void testItem() {
    final String json = "{'data':{'name':'Sample Text','quantity':7},'version':2}";
    final Data<Item> data = dataService.parseItem(json);
    Assert.assertNotNull(data);
    Assert.assertEquals(2, data.getVersion());

    final Item item = data.getData();
    Assert.assertNotNull(item);
    Assert.assertEquals("Sample Text", item.getName());
    Assert.assertEquals(7, item.getQuantity());
  }

  @Test
  public void testPerson() {
    final String json = "{'data':{'name':'Sample Text'},'version':6}";
    final Data<Person> data = dataService.parsePerson(json);
    Assert.assertNotNull(data);
    Assert.assertEquals(6, data.getVersion());

    final Person person = data.getData();
    Assert.assertNotNull(person);
    Assert.assertEquals("Sample Text", person.getName());
  }

}
