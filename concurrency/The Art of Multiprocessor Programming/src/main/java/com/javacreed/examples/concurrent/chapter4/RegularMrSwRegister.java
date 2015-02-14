package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.7")
public class RegularMrSwRegister implements Register<Byte> {

  private static int RANGE = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;

  /** Regular boolean MRSW */
  private final boolean[] bit = new boolean[RegularMrSwRegister.RANGE];

  public RegularMrSwRegister() {
    bit[0] = true;
  }

  @Override
  public Byte read() {
    for (int i = 0; i < RegularMrSwRegister.RANGE; i++) {
      if (bit[i]) {
        return (byte) i;
      }
    }
    return -1;
  }

  @Override
  public void write(final Byte v) {
    bit[v] = true;
    for (int i = v - 1; i >= 0; i--) {
      bit[i] = false;
    }
  }

}
