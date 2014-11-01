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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.javacreed.api.csv.CsvException;
import com.javacreed.api.csv.stream.CsvWriter;

public class ReflectCsvTypeAdapter<T> extends AbstractCsvWithHeaderTypeAdapter<T> {

  public static class Builder<E> {
    private InstanceCreator<E> instanceCreator;
    private FieldsValuesSetter<E> fieldsValuesSetter;
    private FieldsNamesTranslator fieldsNamesTranslator;
    private Map<String, Field> fieldsByName;

    public ReflectCsvTypeAdapter<E> build() {
      if (instanceCreator == null) {
        throw new CsvException("Instance creator is not set");
      }

      if (fieldsValuesSetter == null) {
        throw new CsvException("Fields values setter is not set");
      }

      if (fieldsNamesTranslator == null && fieldsByName == null) {
        throw new CsvException("Fields names translator is not set");
      }

      if (fieldsByName == null) {
        fieldsByName = fieldsNamesTranslator.getFieldsByNameMap();
      }

      return new ReflectCsvTypeAdapter<>(instanceCreator, fieldsValuesSetter, fieldsByName);
    }

    public Builder<E> setFieldsNamesTranslator(final FieldsNamesTranslator fieldsNamesTranslator) {
      this.fieldsNamesTranslator = Objects.requireNonNull(fieldsNamesTranslator);
      return this;
    }

    public Builder<E> setFieldsValuesSetter(final FieldsValuesSetter<E> fieldsValuesSetter) {
      this.fieldsValuesSetter = Objects.requireNonNull(fieldsValuesSetter);
      return this;
    }

    public Builder<E> setInstanceCreator(final InstanceCreator<E> instanceCreator) {
      this.instanceCreator = Objects.requireNonNull(instanceCreator);
      return this;
    }

    public Builder<E> useDefaults(final Class<E> type) {
      Objects.requireNonNull(type);
      setInstanceCreator(new DefaultInstanceCreator<E>(type));
      setFieldsValuesSetter(new DefaultFieldsValuesSetter<E>());
      setFieldsNamesTranslator(new DefaultFieldsNamesTranslater(type));
      return this;
    }
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

  private static class DefaultFieldsValuesSetter<E> implements FieldsValuesSetter<E> {
    @Override
    public void setValue(final Field field, final E object, final String value) {
      final boolean accessible = field.isAccessible();
      try {
        field.setAccessible(true);
        final Class<?> fieldType = field.getType();
        if (fieldType == Byte.TYPE) {
          field.setByte(object, Byte.parseByte(value));
        } else if (fieldType == Short.TYPE) {
          field.setShort(object, Short.parseShort(value));
        } else if (fieldType == Integer.TYPE) {
          field.setInt(object, Integer.parseInt(value));
        } else if (fieldType == Long.TYPE) {
          field.setLong(object, Long.parseLong(value));
        } else if (fieldType == Float.TYPE) {
          field.setFloat(object, Float.parseFloat(value));
        } else if (fieldType == Double.TYPE) {
          field.setDouble(object, Double.parseDouble(value));
        } else if (fieldType == String.class) {
          field.set(object, value);
        } else {
          // TODO: need to support more types such as date. Also we need to be more pluggable and allow the user to
          // specify how each field is to be parsed
          throw new CsvException("Unsupported field type: " + fieldType.getCanonicalName());
        }

      } catch (IllegalAccessException | RuntimeException e) {
        throw CsvException.handle("Failed to set the value: '" + value + "' to field " + field.getName(), e);
      } finally {
        field.setAccessible(accessible);
      }
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

  public interface FieldsValuesSetter<E> {
    void setValue(Field field, E t, String value);
  }

  public interface InstanceCreator<E> {
    E createInstance();
  }

  private final InstanceCreator<T> instanceCreator;
  private final FieldsValuesSetter<T> fieldsValuesSetter;
  private final Map<String, Field> fieldsByName;

  public ReflectCsvTypeAdapter(final InstanceCreator<T> instanceCreator,
      final FieldsValuesSetter<T> fieldsValuesSetter, final Map<String, Field> fieldsByName) {
    this.instanceCreator = Objects.requireNonNull(instanceCreator);
    this.fieldsValuesSetter = Objects.requireNonNull(fieldsValuesSetter);
    this.fieldsByName = new HashMap<>(Objects.requireNonNull(fieldsByName));
  }

  @Override
  protected T processLine(final RowData rowData) throws CsvException, IOException {
    final T t = instanceCreator.createInstance();

    for (final Entry<String, Field> entry : fieldsByName.entrySet()) {
      final String columnName = entry.getKey();
      if (rowData.containsHeader(columnName)) {
        final String value = rowData.getValue(columnName);
        fieldsValuesSetter.setValue(entry.getValue(), t, value);
      }
    }

    return t;
  }

  @Override
  protected void writeHeaders(final CsvWriter out, final T value) throws CsvException, IOException,
      UnsupportedOperationException {
    // TODO: think about this
    throw new UnsupportedOperationException();
  }

}
