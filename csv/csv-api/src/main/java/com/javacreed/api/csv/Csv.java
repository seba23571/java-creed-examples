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
package com.javacreed.api.csv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.javacreed.api.csv.adapter.CsvTypeAdapter;
import com.javacreed.api.csv.adapter.CsvTypeAdapterFactory;
import com.javacreed.api.csv.adapter.ReflectCsvTypeAdapter;
import com.javacreed.api.csv.stream.AppendableCsvWriter;
import com.javacreed.api.csv.stream.CsvReader;
import com.javacreed.api.csv.stream.CsvReaderFactory;
import com.javacreed.api.csv.stream.CsvWriter;
import com.javacreed.api.csv.stream.CsvWriterFactory;
import com.javacreed.api.csv.stream.ReadableCsvReader;

public class Csv {

  public static class Builder {

    private final CsvReaderFactory csvReaderFactory = new ReadableCsvReader.Factory();
    private final CsvWriterFactory csvWriterFactory = new AppendableCsvWriter.Factory();

    private final Map<Class<?>, CsvTypeAdapter<?>> typeAdapters = new LinkedHashMap<>();
    private final Map<Class<?>, CsvTypeAdapterFactory<?>> typeAdaptersFactories = new LinkedHashMap<>();;

    public Csv build() {
      return new Csv(this);
    }

    public <T> Builder registerTypeAdapter(final Class<T> type, final CsvTypeAdapter<T> csvTypeAdapter) {
      typeAdapters.put(type, csvTypeAdapter);
      return this;
    }

    public <T> Builder registerTypeAdapterFactory(final Class<T> type, final CsvTypeAdapterFactory<T> csvTypeAdapter) {
      typeAdaptersFactories.put(type, csvTypeAdapter);
      return this;
    }

    public void setValueSeparator(final String valueSeparator) {
      csvReaderFactory.setValueSeparator(valueSeparator);
      csvWriterFactory.setValueSeparator(valueSeparator);
    }
  }

  private final CsvReaderFactory csvReaderFactory;
  private final CsvWriterFactory csvWriterFactory;

  private final Map<Class<?>, CsvTypeAdapter<?>> typeAdapters;
  private final Map<Class<?>, CsvTypeAdapterFactory<?>> typeAdaptersFactories;

  public Csv() {
    this(new Builder());
  }

  private Csv(final Builder builder) {
    this.typeAdapters = Collections.unmodifiableMap(new HashMap<>(builder.typeAdapters));
    this.typeAdaptersFactories = Collections.unmodifiableMap(new HashMap<>(builder.typeAdaptersFactories));
    this.csvReaderFactory = builder.csvReaderFactory;
    this.csvWriterFactory = builder.csvWriterFactory;
  }

  public <T> List<T> fromCsv(final CsvReader in, final Class<T> type) {
    try {
      final CsvTypeAdapter<T> typeAdapter = getTypeAdapter(type);

      final List<T> list = new LinkedList<>();
      while (in.hasMoreLines()) {
        final T t = typeAdapter.read(in);
        list.add(t);
      }
      return list;
    } catch (final Exception e) {
      throw handleException(e);
    }
  }

  public <T> List<T> fromCsv(final File file, final String encoding, final Class<T> type) {
    try {
      return fromCsv(new BufferedInputStream(new FileInputStream(file)), encoding, type);
    } catch (final Exception e) {
      throw handleException(e);
    }
  }

  public <T> List<T> fromCsv(final InputStream in, final String encoding, final Class<T> type) {
    try {
      return fromCsv(new InputStreamReader(in, encoding), type);
    } catch (final UnsupportedEncodingException e) {
      throw handleException(e);
    }
  }

  public <T> List<T> fromCsv(final Readable readable, final Class<T> type) {
    try {
      return fromCsv(csvReaderFactory.create(readable), type);
    } catch (final Exception e) {
      throw handleException(e);
    }
  }

  public <T> List<T> fromCsv(final String csv, final Class<T> type) {
    if (csv == null) {
      return null;
    }

    return fromCsv(new StringReader(csv), type);
  }

  public <T> CsvTypeAdapter<T> getTypeAdapter(final Class<T> type) {
    for (final Entry<Class<?>, CsvTypeAdapter<?>> entry : typeAdapters.entrySet()) {
      if (entry.getKey() == type) {
        @SuppressWarnings("unchecked")
        final CsvTypeAdapter<T> t = (CsvTypeAdapter<T>) entry.getValue();
        return t;
      }
    }

    for (final Entry<Class<?>, CsvTypeAdapterFactory<?>> entry : typeAdaptersFactories.entrySet()) {
      if (entry.getKey() == type) {
        @SuppressWarnings("unchecked")
        final CsvTypeAdapterFactory<T> t = (CsvTypeAdapterFactory<T>) entry.getValue();
        return t.build();
      }
    }

    return new ReflectCsvTypeAdapter.Builder<T>().useDefaults(type).build();
  }

  private CsvException handleException(final Throwable e) {
    if (e instanceof CsvException) {
      return (CsvException) e;
    } else {
      return new CsvException(e);
    }
  }

  public <T> void toCsv(final Appendable appendable, final List<? extends T> list, final Class<T> type) {
    try {
      toCsv(csvWriterFactory.create(appendable), list, type);
    } catch (final Exception e) {
      throw handleException(e);
    }
  }

  public <T> void toCsv(final CsvWriter out, final List<? extends T> list, final Class<T> type) {
    try {
      final CsvTypeAdapter<T> typeAdapter = getTypeAdapter(type);

      for (final T item : list) {
        typeAdapter.write(out, item);
      }
    } catch (final Exception e) {
      throw handleException(e);
    }
  }

}
