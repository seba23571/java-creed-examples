package com.javacreed.examples.gson.part2;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class DataTypeAdapterFactory implements TypeAdapterFactory {

  public static class Builder {
    private final Map<Type, AbstractTypeAdapter<?>> adapters = new HashMap<>();

    public <T> Builder add(final Type type, final AbstractTypeAdapter<T> adapter) {
      Objects.requireNonNull(type);
      Objects.requireNonNull(adapter);
      adapters.put(type, adapter);
      return this;
    }

    public DataTypeAdapterFactory build() {
      return new DataTypeAdapterFactory(this);
    }
  }

  private final Map<Type, AbstractTypeAdapter<?>> adapters = new HashMap<>();

  private DataTypeAdapterFactory(final Builder builder) {
    adapters.putAll(builder.adapters);
  }

  @Override
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
    @SuppressWarnings("unchecked")
    final Class<T> rawType = (Class<T>) type.getRawType();

    @SuppressWarnings("unchecked")
    final AbstractTypeAdapter<T> typeAdapter = (AbstractTypeAdapter<T>) adapters.get(rawType);
    if (typeAdapter != null) {
      typeAdapter.setGson(gson);
    }
    return typeAdapter;
  }
}
