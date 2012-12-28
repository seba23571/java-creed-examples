package com.javacreed.examples.gson.part3;

public class Id<T> {

  public static <N> Id<N> createEmptyInstance(final Class<N> type) {
    return new Id<N>(type, 0);
  }

  public transient final Class<T> type;
  public final int id;

  public Id(final Class<T> type, final int id) {
    this.type = type;
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public Class<T> getType() {
    return type;
  }

  @Override
  public String toString() {
    return String.format("%d (of type %s)", id, type == null ? "unknown" : type.getSimpleName());
  }

}
