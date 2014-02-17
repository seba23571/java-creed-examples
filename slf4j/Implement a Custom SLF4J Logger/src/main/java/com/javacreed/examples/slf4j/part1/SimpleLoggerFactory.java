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
package com.javacreed.examples.slf4j.part1;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.jcip.annotations.ThreadSafe;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

@ThreadSafe
public class SimpleLoggerFactory implements ILoggerFactory {

  private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();

  @Override
  public Logger getLogger(final String name) {
    final String loggerName = name.toLowerCase();

    final Logger logger = loggerMap.get(loggerName);
    if (logger != null) {
      return logger;
    }

    loggerMap.putIfAbsent(loggerName, new SimpleLogger(name));
    return loggerMap.get(loggerName);
  }

}
