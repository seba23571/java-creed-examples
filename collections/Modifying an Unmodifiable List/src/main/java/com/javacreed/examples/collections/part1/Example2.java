/*
 * #%L
 * Modifying an Unmodifiable List
 * $Id:$
 * $HeadURL$
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
package com.javacreed.examples.collections.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Example2 {

  public static void main(final String[] args) {
    final List<String> modifiable = new ArrayList<>();
    modifiable.add("Java");
    modifiable.add("is");

    // Here we are creating a new array list
    final List<String> unmodifiable = Collections.unmodifiableList(new ArrayList<>(modifiable));
    System.out.println("Before modification: " + unmodifiable);

    modifiable.add("the");
    modifiable.add("best");

    System.out.println("After modification: " + unmodifiable);
  }

}
