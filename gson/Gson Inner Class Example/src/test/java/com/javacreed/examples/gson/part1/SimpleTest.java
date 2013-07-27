package com.javacreed.examples.gson.part1;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleTest {

  @Test
  public void test() {
    final String json = "{'item':{'name':'Sample Text'}}";

    final Gson gson = new GsonBuilder().create();
    final OuterClass object = gson.fromJson(json, OuterClass.class);
    Assert.assertNotNull(object);
    Assert.assertNotNull(object.getItem());
    Assert.assertEquals("Sample Text", object.getItem().getName());
  }
}
