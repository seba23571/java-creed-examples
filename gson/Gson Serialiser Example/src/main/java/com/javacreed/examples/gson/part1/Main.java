package com.javacreed.examples.gson.part1;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(final String[] args) throws IOException {
        // Configure GSON
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Book.class, new BookSerialiser());
        gsonBuilder.setPrettyPrinting();
        final Gson gson = gsonBuilder.create();

        final Book book = new Book();
        book.setTitle("Java Puzzlers: Traps, Pitfalls, and Corner Cases");
        book.setIsbn10("032133678X");
        book.setIsbn13("978-0321336781");
        book.setAuthors(new String[] { "Joshua Bloch", "Neal Gafter" });

        // Format to JSON
        final String json = gson.toJson(book);
        System.out.println(json);
    }
}
