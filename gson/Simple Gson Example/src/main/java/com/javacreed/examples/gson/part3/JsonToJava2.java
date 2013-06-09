package com.javacreed.examples.gson.part3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToJava2 {
  public static void main(final String[] args) throws IOException {
    try (final Reader reader = new InputStreamReader(JsonToJava2.class.getResourceAsStream("/Server2.json"))) {

      final Gson gson = new GsonBuilder().create();
      final Person2 p = gson.fromJson(reader, Person2.class);
      System.out.println(p);
    }
  }
}
