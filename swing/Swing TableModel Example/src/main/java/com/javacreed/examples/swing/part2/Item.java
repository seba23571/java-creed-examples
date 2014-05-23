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
package com.javacreed.examples.swing.part2;

import java.util.ArrayList;
import java.util.Collection;

public class Item {

    public static Collection<Item> createSample() {
        final Collection<Item> collection = new ArrayList<>();
        collection.add(new Item(1, "Car"));
        collection.add(new Item(2, "Phone"));
        collection.add(new Item(3, "Computer"));
        collection.add(new Item(4, "Table"));
        collection.add(new Item(5, "TV"));
        return collection;
    }

    private int id;
    private String name;

    public Item() {}

    public Item(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
