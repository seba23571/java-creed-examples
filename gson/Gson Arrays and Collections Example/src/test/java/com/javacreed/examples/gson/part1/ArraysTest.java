package com.javacreed.examples.gson.part1;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ArraysTest {

  @Test
  public void testArray() {
    final String json = "['First Element',2,{name:'Sample Text'},[1,2,3,4,{'name':'yet another object'}]]";
    final Gson gson = new GsonBuilder().create();

    final Type type = new TypeToken<List<Object>>() {}.getType();
    final List<Object> list = gson.fromJson(json, type);
    for (final Object element : list) {
      System.out.println(element.getClass().getCanonicalName());
    }
  }
}
