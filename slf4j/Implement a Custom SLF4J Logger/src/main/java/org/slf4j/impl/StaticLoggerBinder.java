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
package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import com.javacreed.examples.slf4j.part1.SimpleLoggerFactory;

public class StaticLoggerBinder implements LoggerFactoryBinder {

  private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

  public static String REQUESTED_API_VERSION = "1.7.5";

  /**
   * Required as from version 1.6.x
   * 
   * @return the singleton instance
   */
  public static final StaticLoggerBinder getSingleton() {
    return StaticLoggerBinder.SINGLETON;
  }

  /**
   * The logger factory that will be used. In reality there will be only one logger factory per implementation (or
   * project) but here we have two examples and part 1 uses a different logger factory from part 2.
   * 
   * Used for <strong>part 1</strong>
   */
  private final ILoggerFactory loggerFactory = new SimpleLoggerFactory();

  // /**
  // * The logger factory that will be used. In reality there will be only one logger factory per implementation (or
  // * project) but here we have two examples and part 1 uses a different logger factory from part 2.
  // *
  // * Used for <strong>part 2</strong>
  // */
  // private final ILoggerFactory loggerFactory = new AsynchLoggerFactory();

  @Override
  public ILoggerFactory getLoggerFactory() {
    return loggerFactory;
  }

  @Override
  public String getLoggerFactoryClassStr() {
    return loggerFactory.getClass().getCanonicalName();
  }
}
