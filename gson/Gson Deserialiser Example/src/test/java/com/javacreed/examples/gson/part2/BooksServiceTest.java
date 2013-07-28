package com.javacreed.examples.gson.part2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class BooksServiceTest {

  private final BooksService booksService = new BooksServiceImpl();

  @Test
  public void test() throws IOException {
    try (InputStream in = getClass().getResourceAsStream("/books2.json")) {
      final String json = IOUtils.toString(in, "UTF-8");

      final Books books = booksService.parseBooks(json);
      Assert.assertNotNull(books);
      Assert.assertEquals(4, books.getBooks().size());
      Assert.assertEquals(8, books.getAuthors().size());
    }
  }
}
