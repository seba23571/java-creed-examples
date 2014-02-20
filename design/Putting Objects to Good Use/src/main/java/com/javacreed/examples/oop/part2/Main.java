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
package com.javacreed.examples.oop.part2;

public class Main {

  public static void main(final String[] args) {
    final Basket basket = new Basket();
    basket.add(new Item("Book", "48.50", "0"));
    basket.add(new Item("Imported Calculator", "12.25", "0.26"));
    basket.add(new Item("Imported Medicine", "8.40", "0.03"));

    System.out.println(basket);
  }
}
