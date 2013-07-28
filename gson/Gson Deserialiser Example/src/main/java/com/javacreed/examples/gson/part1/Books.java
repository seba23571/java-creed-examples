package com.javacreed.examples.gson.part1;

import java.util.ArrayList;
import java.util.List;

public class Books {

  private final List<String> booksTitles = new ArrayList<>();

  public void addBookTitle(final String title) {
    booksTitles.add(title);
  }

  public List<String> getBooksTitles() {
    return booksTitles;
  }

  @Override
  public String toString() {
    return booksTitles.toString();
  }
}
