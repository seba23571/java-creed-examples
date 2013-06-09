package com.javacreed.examples.gson.part2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToJava {
  public static void main(final String[] args) throws IOException {
    try (Reader reader = new InputStreamReader(JsonToJava.class.getResourceAsStream("/Server1.json"))) {

      final Gson gson = new GsonBuilder().create();
      final Person p = gson.fromJson(reader, Person.class);
      System.out.println(p);
    }
  }
}
