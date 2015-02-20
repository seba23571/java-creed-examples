/*
 * #%L
 * Saving Space with String Caching
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
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
package com.javacreed.examples.lang.part3;

import java.util.HashMap;
import java.util.Map;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class StringPool {

  private final Map<String, String> cache = new HashMap<>();

  public String get(final String value) {
    String cachedValue = cache.get(value);
    if (cachedValue == null) {
      cachedValue = value;
      cache.put(value, value);
    }
    return cachedValue;
  }
}
