package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.appendix.a.ThreadID;
import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.6")
public class SafeBooleanMrSwRegister implements Register<Boolean> {

  /** Array of safe SRSW registers */
  private final boolean[] table;

  public SafeBooleanMrSwRegister(final int capacity) {
    table = new boolean[capacity];
  }

  @Override
  public Boolean read() {
    return table[ThreadID.get()];
  }

  @Override
  public void write(final Boolean v) {
    for (int i = 0; i < table.length; i++) {
      table[i] = v;
    }
  }

}
