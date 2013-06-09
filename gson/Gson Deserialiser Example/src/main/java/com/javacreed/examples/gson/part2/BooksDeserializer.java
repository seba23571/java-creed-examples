package com.javacreed.examples.gson.part2;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BooksDeserializer implements JsonDeserializer<Books> {

  @Override
  public Books deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {
    final Books books = new Books();

    final JsonObject jsonObject = json.getAsJsonObject();
    processBook(books, jsonObject.get("1").getAsJsonObject());
    processBook(books, jsonObject.get("2").getAsJsonObject());
    processBook(books, jsonObject.get("3").getAsJsonObject());
    processBook(books, jsonObject.get("4").getAsJsonObject());

    return books;
  }

  private void processBook(final Books books, final JsonObject jsonBook) {
    final List<Author> bookAuthors = new ArrayList<>();
    final JsonArray authorsArray = jsonBook.get("authors").getAsJsonArray();
    for (int i = 0, size = authorsArray.size(); i < size; i++) {
      final String name = authorsArray.get(i).getAsString();
      Author author = books.getAuthorWithName(name);
      if (author == null) {
        author = new Author(name);
        books.addAuthor(author);
      }
      bookAuthors.add(author);
    }

    final String title = jsonBook.get("title").getAsString();
    final String isbn = jsonBook.get("isbn").getAsString();
    final Book book = new Book(title, isbn, bookAuthors.toArray(new Author[bookAuthors.size()]));

    for (final Author author : bookAuthors) {
      author.addBook(book);
    }

    books.addBook(book);
  }
}
