package com.javacreed.examples.gson.part2_3;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class IdInstanceCreator<T> implements InstanceCreator<Id<T>> {

  private final Class<T> idType;

  public IdInstanceCreator(final Class<T> type) {
    this.idType = type;
  }

  @Override
  public Id<T> createInstance(final Type type) {
    return new Id<T>(this.idType, 0);
  }

}
