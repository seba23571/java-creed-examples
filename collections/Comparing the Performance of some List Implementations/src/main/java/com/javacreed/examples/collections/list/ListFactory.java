/*
 * #%L
 * Comparing the Performance of some List Implementations
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
package com.javacreed.examples.collections.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ListFactory {

  abstract class AbstractListFactory implements ListFactory {
    private final String name;

    public AbstractListFactory(final String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }
  }

  ListFactory ARRAY_LIST_FACTORY = new AbstractListFactory("ArrayList") {
    @Override
    public List<String> create(final int size) {
      return new ArrayList<>();
    }
  };

  ListFactory ARRAY_LIST_WITH_SIZE_FACTORY = new AbstractListFactory("ArrayList with init size") {
    @Override
    public List<String> create(final int size) {
      return new ArrayList<>(size);
    }
  };

  ListFactory VECTOR_WITH_SIZE_FACTORY = new AbstractListFactory("Vector with init size") {
    @Override
    public List<String> create(final int size) {
      return new Vector<>(size);
    }
  };

  ListFactory VECTOR_FACTORY = new AbstractListFactory("Vector") {
    @Override
    public List<String> create(final int size) {
      return new Vector<>();
    }
  };

  ListFactory LINKED_LIST_FACTORY = new AbstractListFactory("LinkedList") {
    @Override
    public List<String> create(final int size) {
      return new LinkedList<>();
    }
  };

  ListFactory STACK_FACTORY = new AbstractListFactory("Stack") {
    @Override
    public List<String> create(final int size) {
      return new Stack<>();
    }
  };

  ListFactory COWAL_FACTORY = new AbstractListFactory("CopyOnWriteArrayList") {
    @Override
    public List<String> create(final int size) {
      return new CopyOnWriteArrayList<>();
    }
  };

  List<String> create(int size);

  String getName();
}
