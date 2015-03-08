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

/**
 * Created by Albert on 07/02/2015.
 */
public class DataParser {

  private final String data;
  private final String[] headers;
  private final String[][] values;

  public DataParser(final String data) {
    this.data = data;

    // TODO: use a better parser
    final String[] lines = this.data.split("\\r\\n|\\n|\\r");
    headers = lines[0].split(",");
    values = new String[lines.length - 1][];
    for (int i = 1, j = 0; i < lines.length; i++, j++) {
      final String[] cells = lines[i].split(",");
      if (cells.length != headers.length) {
        throw new IllegalArgumentException("Invalid");
      }

      values[j] = cells;
    }
  }

  public String[] getHeaders() {
    return headers;
  }

  public String[][] getValues() {
    return values;
  }
}
