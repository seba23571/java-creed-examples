package com.javacreed.examples.gson.part1;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleTest {

  @Test
  public void test() throws Exception {
    String json = IOUtils.toString(getClass().getResourceAsStream("/samples/sample1.json"), "UTF-8");
    
    Gson gson = new GsonBuilder().create();
    OuterClass object = gson.fromJson(json, OuterClass.class);
    Assert.assertNotNull(object);
  }
}
