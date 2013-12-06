package com.javacreed.examples.gson.part2;

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

        final Book book = new Book();
        book.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");
        book.setIsbn("032133678X");
        book.setAuthors(new Author[] { joshuaBloch, nealGafter });

        final String json = gson.toJson(book);
        System.out.println(json);
    }
}
