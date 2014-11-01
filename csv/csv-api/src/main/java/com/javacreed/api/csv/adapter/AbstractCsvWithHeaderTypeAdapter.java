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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javacreed.api.csv.CsvException;
import com.javacreed.api.csv.stream.CsvReader;
import com.javacreed.api.csv.stream.CsvWriter;

public abstract class AbstractCsvWithHeaderTypeAdapter<T> extends AbstractCsvTypeAdapter<T> {

  public static interface RowData {
    boolean containsHeader(String columnName);

    String getValue(int columnIndex);

    String getValue(String columnName);
  }

  private final Map<String, Integer> headerNameToIndex = new HashMap<>();

  private final CsvTypeAdapter<T> headerDelegator = new AbstractCsvTypeAdapter<T>() {
    @Override
    public T read(final CsvReader in) throws CsvException, IOException, UnsupportedOperationException {

      in.beginLine();
      for (int i = 0; in.hasMoreValues(); i++) {
        final String name = in.readValue();
        if (headerNameToIndex.containsKey(name)) {
          throw new CsvException("Duplicate column name '" + name + "'");
        }
        headerNameToIndex.put(name, i);
      }
      in.endLine();

      typeAdapter = rowDelegator;
      return typeAdapter.read(in);
    }

    @Override
    public void write(final CsvWriter out, final T value) throws CsvException, IOException {
      writeHeaders(out);
      rowDelegator.write(out, value);
      typeAdapter = rowDelegator;
    };
  };

  private final CsvTypeAdapter<T> rowDelegator = new AbstractCsvTypeAdapter<T>() {
    @Override
    public T read(final CsvReader in) throws CsvException, IOException, UnsupportedOperationException {
      final List<String> values = new ArrayList<>(headerNameToIndex.size());
      in.beginLine();
      while (in.hasMoreValues()) {
        values.add(in.readValue());
      }
      in.endLine();

      if (values.size() != headerNameToIndex.size()) {
        throw new CsvException("Expected " + headerNameToIndex.size() + " columns but found " + values.size());
      }

      final RowData rowData = new RowData() {
        @Override
        public boolean containsHeader(final String columnName) {
          return headerNameToIndex.containsKey(columnName);
        }

        @Override
        public String getValue(final int columnIndex) {
          return values.get(columnIndex);
        }

        @Override
        public String getValue(final String columnName) {
          final Integer columnIndex = headerNameToIndex.get(columnName);
          if (columnIndex == null) {
            throw new CsvException("Header with name '" + columnName + "' was not found");
          }

          return getValue(columnIndex);
        }
      };

      return processLine(rowData);
    }

    @Override
    public void write(final CsvWriter out, final T value) throws CsvException, IOException,
        UnsupportedOperationException {
      writeValues(out, value);
    }
  };

  private CsvTypeAdapter<T> typeAdapter = headerDelegator;

  protected abstract T processLine(RowData rowData) throws CsvException, IOException;

  @Override
  public final T read(final CsvReader in) throws CsvException, IOException {
    return typeAdapter.read(in);
  }

  @Override
  public void write(final CsvWriter out, final T value) throws CsvException, IOException, UnsupportedOperationException {
    typeAdapter.write(out, value);
  }

  protected void writeHeaders(final CsvWriter out) throws CsvException, IOException, UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  protected void writeValues(final CsvWriter out, final T value) throws CsvException, IOException,
      UnsupportedOperationException {
    throw new UnsupportedOperationException();
  };

}
