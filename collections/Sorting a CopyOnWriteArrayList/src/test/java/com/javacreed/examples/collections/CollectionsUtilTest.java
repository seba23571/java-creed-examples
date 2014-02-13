package com.javacreed.examples.collections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Assert;
import org.junit.Test;

public class CollectionsUtilTest {

  @Test
  public void test() {
    final List<String> list = new CopyOnWriteArrayList<>();

    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "3"));
    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "2"));
    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "1"));

    Assert.assertEquals(-1, CollectionsUtil.addInOrder(list, "3"));

    Assert.assertEquals(3, list.size());
    Assert.assertEquals("1", list.get(0));
    Assert.assertEquals("2", list.get(1));
    Assert.assertEquals("3", list.get(2));
  }

}
