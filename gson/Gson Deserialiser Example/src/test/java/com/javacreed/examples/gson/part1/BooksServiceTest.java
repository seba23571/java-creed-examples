package com.javacreed.examples.gson.part1;

import org.junit.Assert;
import org.junit.Test;

public class BooksServiceTest {

  private final BooksService booksService = new BooksServiceImpl();

  @Test
  public void test() {
    final String json =//@formatter:off
        "{'1': 'Effective Java (2nd Edition)'," +
    		" '2': 'JavaTM Puzzlers: Traps, Pitfalls, and Corner Cases'," +
    		" '3': 'Java Concurrency in Practice'," +
    		" '4': 'Java: The Good Parts'}";//@formatter:on

    final Books books = booksService.parseBooks(json);
    Assert.assertNotNull(books);
    Assert.assertEquals(4, books.getBooksTitles().size());
    Assert.assertEquals("Effective Java (2nd Edition)", books.getBooksTitles().get(0));
  }

}
