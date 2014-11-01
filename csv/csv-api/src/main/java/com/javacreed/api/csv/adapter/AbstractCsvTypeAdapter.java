/*
 * #%L
 * JavaCreed CSV API
 * $Id:$
 * $HeadURL:$
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
package com.javacreed.api.csv.adapter;

import java.io.IOException;

import com.javacreed.api.csv.CsvException;
import com.javacreed.api.csv.stream.CsvReader;
import com.javacreed.api.csv.stream.CsvWriter;

public abstract class AbstractCsvTypeAdapter<T> implements CsvTypeAdapter<T> {

  @Override
  public T read(final CsvReader in) throws CsvException, IOException, UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void write(final CsvWriter out, final T value) throws CsvException, IOException, UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
}
