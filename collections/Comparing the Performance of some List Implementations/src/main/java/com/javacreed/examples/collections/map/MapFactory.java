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
package com.javacreed.examples.collections.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public interface MapFactory {

  abstract class AbstractMapFactory implements MapFactory {
    private final String name;

    public AbstractMapFactory(final String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }
  }

  MapFactory HASH_MAP_FACTORY = new AbstractMapFactory("HashMap") {
    @Override
    public Map<String, String> create(final int size) {
      return new HashMap<>();
    }
  };

  MapFactory HASH_MAP_WITH_SIZE_FACTORY = new AbstractMapFactory("HashMap with init size") {
    @Override
    public Map<String, String> create(final int size) {
      return new HashMap<>(size);
    }
  };

  MapFactory TREE_MAP_FACTORY = new AbstractMapFactory("TreeMap") {
    @Override
    public Map<String, String> create(final int size) {
      return new TreeMap<>();
    }
  };

  MapFactory HASHTABLE_FACTORY = new AbstractMapFactory("Hashtable") {
    @Override
    public Map<String, String> create(final int size) {
      return new Hashtable<>();
    }
  };

  MapFactory HASHTABLE_WITH_SIZE_FACTORY = new AbstractMapFactory("Hashtable with init size") {
    @Override
    public Map<String, String> create(final int size) {
      return new Hashtable<>();
    }
  };

  MapFactory LINKED_HASH_MAP_FACTORY = new AbstractMapFactory("LinkedHashMap") {
    @Override
    public Map<String, String> create(final int size) {
      return new LinkedHashMap<>();
    }
  };

  MapFactory LINKED_HASH_MAP_WITH_SIZE_FACTORY = new AbstractMapFactory("LinkedHashMap with init size") {
    @Override
    public Map<String, String> create(final int size) {
      return new LinkedHashMap<>(size);
    }
  };

  Map<String, String> create(int size);

  String getName();
}
