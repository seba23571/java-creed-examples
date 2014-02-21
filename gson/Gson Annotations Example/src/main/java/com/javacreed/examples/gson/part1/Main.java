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
