/*
 * #%L
 * Bulk Mail Java Application
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
package com.javacreed.examples.mail;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MessageParser {

  private final String message;
  private final Set<String> variables;

  public MessageParser(final String message) {
    this.message = message;

    // TODO: add some validations
    final SortedSet<String> variables = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    for (int open = -1; (open = message.indexOf("${", open + 1)) != -1;) {
      final int close = message.indexOf("}", open);
      variables.add(message.substring(open + 2, close));
    }

    this.variables = Collections.unmodifiableSortedSet(variables);
  }

  public String getMessage() {
    return message;
  }

  public Set<String> getVariablesNames() {
    return variables;
  }
}
