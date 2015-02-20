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
package com.javacreed.examples.lang.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example3 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example3.class);

  public static void main(final String[] args) {
    final String a = new String("abcd");
    final String b = new String("abcd");

    Example3.LOGGER.debug("Same? {}", a == b);
    Example3.LOGGER.debug("Same intern? {}", a.intern() == b.intern());
  }
}
