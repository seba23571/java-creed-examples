package com.javacreed.examples.gson.part1_1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleExample3 {
  public static void main(final String[] args) throws IOException {
    // Ideally use try/finally or try-with-resources to close reader
    final Writer writer = new OutputStreamWriter(new FileOutputStream("Output.json"));

    final Gson gson = new GsonBuilder().create();
    gson.toJson("Hello", writer);
    gson.toJson(123, writer);

    writer.close();
  }
}
