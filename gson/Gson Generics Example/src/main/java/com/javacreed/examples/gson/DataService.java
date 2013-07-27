package com.javacreed.examples.gson;

public interface DataService {

  Data<Item> parseItem(final String json);

  Data<Person> parsePerson(final String json);

}
