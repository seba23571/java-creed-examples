package com.javacreed.examples.gson.part5_1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) {

    final GsonBuilder builder = new GsonBuilder();

    final Gson gson = builder.create();

    final Box box = new Box();
    box.setWidth(10);
    box.setHeight(20);
    box.setDepth(30);

    final String json = gson.toJson(box);
    System.out.printf("Serialised: %s%n", json);

    final Box otherBox = gson.fromJson(json, Box.class);
    System.out.printf("Same box: %s%n", box.equals(otherBox));
  }
}
