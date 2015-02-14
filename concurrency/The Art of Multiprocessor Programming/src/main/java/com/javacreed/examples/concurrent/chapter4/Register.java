package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.1")
public interface Register<T> {

  T read();

  void write(T v);
}
