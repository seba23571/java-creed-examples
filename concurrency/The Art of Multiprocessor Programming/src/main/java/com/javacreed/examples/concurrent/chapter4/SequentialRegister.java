package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.2")
public class SequentialRegister<T> implements Register<T> {

  private T value;

  @Override
  public T read() {
    return value;
  }

  @Override
  public void write(final T v) {
    this.value = v;
  }

}
