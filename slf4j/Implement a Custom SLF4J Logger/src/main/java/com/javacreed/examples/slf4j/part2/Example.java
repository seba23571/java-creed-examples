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
package com.javacreed.examples.slf4j.part2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {

  // Make sure to modify the StaticLoggerBinder to use the correct logger
  private static final Logger LOGGER = LoggerFactory.getLogger(Example.class);

  public static void main(final String[] args) {
    final long startTime = System.currentTimeMillis();
    for (int i = 0; i < 2000; i++) {
      Example.LOGGER.debug("Hello. I'm using an asynch custom logger");
    }

    final long taken = System.currentTimeMillis() - startTime;
    System.out.println("Complete in " + taken + " milli seconds");
  }
}
