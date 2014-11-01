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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.javacreed.api.csv.CsvException;
import com.javacreed.api.csv.stream.CsvWriter;

public class ReflectCsvTypeAdapter<T> extends AbstractCsvWithHeaderTypeAdapter<T> {

  public static class Builder<E> {
    private InstanceCreator<E> instanceCreator;
    private FieldsNamesTranslator fieldsNamesTranslator;
    private Map<String, Field> fieldsByName;
    private final Map<Class<?>, CsvFieldAdapter> fieldsAdapters = new HashMap<>();

    public Builder() {
      setDefaultFieldsAdapters();
    }

    public ReflectCsvTypeAdapter<E> build() {
      if (instanceCreator == null) {
        throw new CsvException("Instance creator is not set");
      }

      if (fieldsNamesTranslator == null && fieldsByName == null) {
        throw new CsvException("Fields names translator is not set");
      }

      if (fieldsByName == null) {
        fieldsByName = fieldsNamesTranslator.getFieldsByNameMap();
      }

      return new ReflectCsvTypeAdapter<>(instanceCreator, fieldsByName, fieldsAdapters);
    }

    private void setDefaultFieldsAdapters() {
      fieldsAdapters.put(Byte.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_BYTE_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(Short.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_SHORT_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(Integer.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_INT_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(Long.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_LONG_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(Float.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_FLOAT_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(Double.TYPE, ReflectCsvTypeAdapter.PRIMITIVE_DOUBLE_CSV_FIELD_ADAPTER);
      fieldsAdapters.put(String.class, ReflectCsvTypeAdapter.STRING_CSV_FIELD_ADAPTER);
    }

    public Builder<E> setFieldAdapters(final Map<Class<?>, CsvFieldAdapter> fieldsAdapters) {
      Objects.requireNonNull(fieldsAdapters);
      this.fieldsAdapters.putAll(fieldsAdapters);
      return this;
    }

    public Builder<E> setFieldsNamesTranslator(final FieldsNamesTranslator fieldsNamesTranslator) {
      this.fieldsNamesTranslator = Objects.requireNonNull(fieldsNamesTranslator);
      return this;
    }

    public Builder<E> setInstanceCreator(final InstanceCreator<E> instanceCreator) {
      this.instanceCreator = Objects.requireNonNull(instanceCreator);
      return this;
    }

    public <T> Builder<E> setValueAdapter(final Class<T> type, final CsvFieldAdapter adapter) {
      Objects.requireNonNull(type);
      Objects.requireNonNull(adapter);
      fieldsAdapters.put(type, adapter);
      return this;
    }

    public Builder<E> useDefaults(final Class<E> type) {
      Objects.requireNonNull(type);
      setInstanceCreator(new DefaultInstanceCreator<E>(type));
      setFieldsNamesTranslator(new DefaultFieldsNamesTranslater(type));
      setDefaultFieldsAdapters();
      return this;
    }
  }

  public interface CsvFieldAdapter {

    String getValue(Field field, Object object) throws IllegalAccessException;

    void setValue(Field field, Object object, String text) throws IllegalAccessException;
  }

  private static class DefaultFieldsNamesTranslater implements FieldsNamesTranslator {

    private final Map<String, Field> fieldsByName;

    public DefaultFieldsNamesTranslater(final Class<?> type) {

      final Map<String, Field> fieldsByName = new HashMap<>();

      for (Class<?> c = type; c != null; c = c.getSuperclass()) {
        final Field[] fields = c.getDeclaredFields();
        for (final Field field : fields) {
          if (!fieldsByName.containsKey(field.getName())) {
            fieldsByName.put(field.getName(), field);
          }
        }
      }

      this.fieldsByName = Collections.unmodifiableMap(fieldsByName);
    }

    @Override
    public Map<String, Field> getFieldsByNameMap() {
      return fieldsByName;
    }
  }

  private static class DefaultInstanceCreator<E> implements InstanceCreator<E> {

    private final Constructor<E> constructor;

    public DefaultInstanceCreator(final Class<E> type) {
      try {
        constructor = type.getConstructor();
      } catch (NoSuchMethodException | RuntimeException e) {
        throw CsvException.handle("Default constructor not found", e);
      }
    }

    @Override
    public E createInstance() {
      final boolean accessible = constructor.isAccessible();
      try {
        final E t = constructor.newInstance();
        return t;
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | RuntimeException e) {
        throw CsvException.handle("Failed to create a new instance", e);
      } finally {
        constructor.setAccessible(accessible);
      }
    }
  }

  public interface FieldsNamesTranslator {
    Map<String, Field> getFieldsByNameMap();
  }

  public interface InstanceCreator<E> {
    E createInstance();
  }

  private static final CsvFieldAdapter STRING_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return (String) field.get(object);
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.set(object, text);
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_BYTE_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getByte(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setByte(object, Byte.parseByte(text));
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_SHORT_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getShort(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setShort(object, Short.parseShort(text));
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_LONG_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getLong(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setLong(object, Long.parseLong(text));
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_FLOAT_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getFloat(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setFloat(object, Float.parseFloat(text));
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_DOUBLE_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getDouble(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setDouble(object, Double.parseDouble(text));
    }
  };

  private static final CsvFieldAdapter PRIMITIVE_INT_CSV_FIELD_ADAPTER = new CsvFieldAdapter() {
    @Override
    public String getValue(final Field field, final Object object) throws IllegalAccessException {
      return String.valueOf(field.getInt(object));
    }

    @Override
    public void setValue(final Field field, final Object object, final String text) throws NumberFormatException,
        IllegalArgumentException, IllegalAccessException {
      field.setInt(object, Integer.parseInt(text));
    }
  };

  private final InstanceCreator<T> instanceCreator;
  private final Map<String, Field> fieldsByName;
  private final Map<Class<?>, CsvFieldAdapter> fieldsAdapters;

  public ReflectCsvTypeAdapter(final InstanceCreator<T> instanceCreator, final Map<String, Field> fieldsByName,
      final Map<Class<?>, CsvFieldAdapter> fieldsAdapters) {
    this.instanceCreator = Objects.requireNonNull(instanceCreator);
    this.fieldsByName = new LinkedHashMap<>(Objects.requireNonNull(fieldsByName));
    this.fieldsAdapters = new HashMap<>(Objects.requireNonNull(fieldsAdapters));
  }

  protected CsvFieldAdapter findFieldAdapter(final Class<?> fieldType) {
    return fieldsAdapters.get(fieldType);
  }

  @Override
  protected T processLine(final RowData rowData) throws CsvException, IOException {
    final T t = instanceCreator.createInstance();

    for (final Entry<String, Field> entry : fieldsByName.entrySet()) {
      final String columnName = entry.getKey();
      if (rowData.containsHeader(columnName)) {
        final String value = rowData.getValue(columnName);
        final Field field = entry.getValue();
        final boolean accessible = field.isAccessible();
        try {
          field.setAccessible(true);
          final Class<?> fieldType = field.getType();
          final CsvFieldAdapter fieldAdapter = findFieldAdapter(fieldType);
          if (fieldAdapter == null) {
            throw new CsvException("Unsupported field type: " + fieldType.getCanonicalName());
          }
          fieldAdapter.setValue(entry.getValue(), t, value);
        } catch (IllegalAccessException | RuntimeException e) {
          throw CsvException.handle("Failed to set the value: '" + value + "' to field " + field.getName(), e);
        } finally {
          field.setAccessible(accessible);
        }

      }
    }

    return t;
  }

  @Override
  protected void writeHeaders(final CsvWriter out) throws CsvException, IOException, UnsupportedOperationException {
    out.beginLine();
    for (final String name : fieldsByName.keySet()) {
      out.writeValue(name);
    }
    out.endLine();
  }

  @Override
  protected void writeValues(final CsvWriter out, final T object) throws CsvException, IOException,
      UnsupportedOperationException {
    out.beginLine();
    for (final Entry<String, Field> entry : fieldsByName.entrySet()) {
      final Field field = entry.getValue();

      final boolean accessible = field.isAccessible();
      try {
        field.setAccessible(true);
        final Class<?> fieldType = field.getType();
        final CsvFieldAdapter fieldAdapter = findFieldAdapter(fieldType);
        if (fieldAdapter == null) {
          throw new CsvException("Unsupported field type: " + fieldType.getCanonicalName());
        }

        final String value = fieldAdapter.getValue(field, object);
        out.writeValue(value);
      } catch (IllegalAccessException | RuntimeException e) {
        throw CsvException.handle("Failed to set the value: '" + object + "' to field " + field.getName(), e);
      } finally {
        field.setAccessible(accessible);
      }

    }
    out.endLine();
  }

}
