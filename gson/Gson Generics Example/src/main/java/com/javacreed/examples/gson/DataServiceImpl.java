package com.javacreed.examples.gson;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataServiceImpl implements DataService {

  private final Gson gson = new GsonBuilder().create();

  // This will not work
  // public <T> Data<T> parseData(final String json) {
  // final Type type = new TypeToken<Data<T>>() {}.getType();
  // return gson.fromJson(json, type);
  // }

  @Override
  public Data<Item> parseItem(final String json) {
    final Type type = new TypeToken<Data<Item>>() {}.getType();
    return gson.fromJson(json, type);
  }

  @Override
  public Data<Person> parsePerson(final String json) {
    final Type type = new TypeToken<Data<Person>>() {}.getType();
    return gson.fromJson(json, type);
  }

}
