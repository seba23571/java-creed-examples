/*
 * #%L
 * Sorting a CopyOnWriteArrayList
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
package com.javacreed.examples.collections;

import java.util.Collections;
import java.util.List;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class CollectionsUtil {

  public static <T extends Comparable<T>> int addInOrder(final List<T> list, final T item) {
    final int insertAt;
    // The index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
    final int index = Collections.binarySearch(list, item);
    if (index < 0) {
      insertAt = -(index + 1);
    } else {
      insertAt = index + 1;
    }

    list.add(insertAt, item);
    return insertAt;
  }

  private CollectionsUtil() {
  }
}
