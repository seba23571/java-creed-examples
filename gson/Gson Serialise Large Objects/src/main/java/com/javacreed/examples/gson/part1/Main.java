/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.gson.part1;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
  public static void main(final String[] args) throws IOException {
    // Configure GSON
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LargeData.class, new LargeDataSerialiser());
    gsonBuilder.setPrettyPrinting();

    final Gson gson = gsonBuilder.create();

    final LargeData data = new LargeData();
    data.create(10485760);

    final String json = gson.toJson(data);

    final File dir = new File("target/part1");
    dir.mkdirs();

    try (PrintStream out = new PrintStream(new File(dir, "output.json"), "UTF-8")) {
      out.println(json);
    }

    System.out.println("Done");
  }
}
