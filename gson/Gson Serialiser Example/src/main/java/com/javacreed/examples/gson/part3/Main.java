package com.javacreed.examples.gson.part3;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

  public static void main(final String[] args) throws IOException {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Book.class, new BookSerialiser());
    gsonBuilder.registerTypeAdapter(Author.class, new AuthorSerialiser());
    gsonBuilder.setPrettyPrinting();
    final Gson gson = gsonBuilder.create();

    final Author joshuaBloch = new Author();
    joshuaBloch.setId(1);
    joshuaBloch.setName("Joshua Bloch");

    final Author nealGafter = new Author();
    nealGafter.setId(2);
    nealGafter.setName("Neal Gafter");

    final Book javaPuzzlers = new Book();
    javaPuzzlers.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");
    javaPuzzlers.setIsbn("032133678X");
    javaPuzzlers.setAuthors(new Author[] { joshuaBloch, nealGafter });

    final Book effectiveJava = new Book();
    effectiveJava.setTitle("Effective Java (2nd Edition)");
    effectiveJava.setIsbn("0321356683");
    effectiveJava.setAuthors(new Author[] { joshuaBloch });

    final Author[] authors = new Author[] { joshuaBloch, nealGafter };
    final Book[] books = new Book[] { javaPuzzlers, effectiveJava };

    final Data data = new Data();
    data.setAuthors(authors);
    data.setBooks(books);

    final String json = gson.toJson(data);
    System.out.println(json);
  }
}
