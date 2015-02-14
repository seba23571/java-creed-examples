package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.10")
public class StampedValue<T> {

  public static <E> StampedValue<E> max(final StampedValue<E> a, final StampedValue<E> b) {
    if (a.stamp > b.stamp) {
      return a;
    }

    return b;
  }

  private final long stamp;

  private final T value;

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final StampedValue MIN_VALUE = new StampedValue(null);

  /**
   * Later values with timestamp provided
   *
   * @param stamp
   * @param value
   */
  public StampedValue(final long stamp, final T value) {
    this.stamp = stamp;
    this.value = value;
  }

  /**
   * Initial value with zero timestamp
   *
   * @param value
   */
  public StampedValue(final T value) {
    this(0, value);
  }

  public long getStamp() {
    return stamp;
  }

  public T getValue() {
    return value;
  }

}
