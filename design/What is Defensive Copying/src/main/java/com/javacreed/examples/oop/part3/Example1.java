/*
 * #%L
 * What is Defensive Copying
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.oop.part3;

import java.util.List;

public class Example1 {
  public static void main(final String[] args) {
    final Data data = new Data();
    data.addValue(5);

    final List<Integer> values = data.getValues();
    values.set(0, -10);

    System.out.println(data);
  }
}
