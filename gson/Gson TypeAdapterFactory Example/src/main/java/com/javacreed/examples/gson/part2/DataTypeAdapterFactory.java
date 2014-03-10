package com.javacreed.examples.gson.part2;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class DataTypeAdapterFactory implements TypeAdapterFactory {

  private final Map<Class<?>, AbstractTypeAdapter<?>> adaptors = new HashMap<>();

  public DataTypeAdapterFactory() {
    adaptors.put(Book.class, new BookTypeAdapter());
  }

  @Override
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
    @SuppressWarnings("unchecked")
    final Class<T> rawType = (Class<T>) type.getRawType();

    @SuppressWarnings("unchecked")
    final AbstractTypeAdapter<T> typeAdapter = (AbstractTypeAdapter<T>) adaptors.get(rawType);
    if (typeAdapter != null) {
      typeAdapter.setGson(gson);
    }
    return typeAdapter;
  }
}
