package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.7")
public class RegularBooleanMrSwRegister implements Register<Boolean> {

  private ThreadLocal<Boolean> last;

  /** Safe MRSW registers */
  private boolean value;

  public RegularBooleanMrSwRegister() {
    last = new ThreadLocal<Boolean>() {
      @Override
      protected Boolean initialValue() {
        return false;
      }
    };
  }

  @Override
  public Boolean read() {
    return value;
  }

  @Override
  public void write(final Boolean v) {
    if (v != last.get()) {
      last.set(v);
      value = v;
    }
  }

}
