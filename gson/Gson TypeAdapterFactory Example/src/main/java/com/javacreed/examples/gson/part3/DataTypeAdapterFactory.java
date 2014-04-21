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
package com.javacreed.examples.gson.part3;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class DataTypeAdapterFactory implements TypeAdapterFactory {

  public static class Builder {
    private final Map<Type, AbstractTypeAdapter<?>> adapters = new HashMap<>();

    public <T, E extends T> Builder add(final Class<T> type, final AbstractTypeAdapter<E> adapter) {
      return checkAndAdd(type, adapter);
    }

    public <T, E extends T> Builder add(final Type type, final AbstractTypeAdapter<E> adapter) {
      return checkAndAdd(type, adapter);
    }

    public DataTypeAdapterFactory build() {
      return new DataTypeAdapterFactory(this);
    }

    private <T> Builder checkAndAdd(final Type type, final AbstractTypeAdapter<T> adapter) {
      Objects.requireNonNull(type);
      Objects.requireNonNull(adapter);
      adapters.put(type, adapter);
      return this;
    }
  }

  private final Map<Type, AbstractTypeAdapter<?>> adapters = new HashMap<>();

  private DataTypeAdapterFactory(final Builder builder) {
    adapters.putAll(builder.adapters);
  }

  @Override
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
    final AbstractTypeAdapter<T> typeAdapter = findTypeAdapter(typeToken);
    if (typeAdapter != null) {
      typeAdapter.setGson(gson);
    }
    return typeAdapter;
  }

  private <T> AbstractTypeAdapter<T> findTypeAdapter(final TypeToken<T> typeToken) {
    @SuppressWarnings("unchecked")
    final Class<T> rawType = (Class<T>) typeToken.getRawType();

    Class<?> selectedTypeAdapterType = null;
    AbstractTypeAdapter<?> selectedTypeAdapter = null;

    for (final Entry<Type, AbstractTypeAdapter<?>> entry : adapters.entrySet()) {
      final Type typeAdapterType = entry.getKey();
      if (rawType == typeAdapterType) {
        selectedTypeAdapter = entry.getValue();
        break;
      }

      if (typeAdapterType instanceof Class) {
        final Class<?> typeAdapterClass = (Class<?>) typeAdapterType;
        if (typeAdapterClass.isAssignableFrom(rawType)) {
          if (selectedTypeAdapterType == null || selectedTypeAdapterType.isAssignableFrom(typeAdapterClass)) {
            selectedTypeAdapterType = rawType;
            selectedTypeAdapter = entry.getValue();
          }
        }
      }
    }

    @SuppressWarnings("unchecked")
    final AbstractTypeAdapter<T> t = (AbstractTypeAdapter<T>) selectedTypeAdapter;
    return t;
  }
}
