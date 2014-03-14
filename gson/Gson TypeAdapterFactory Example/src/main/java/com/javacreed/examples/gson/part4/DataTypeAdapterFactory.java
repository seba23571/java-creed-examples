package com.javacreed.examples.gson.part4;

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
