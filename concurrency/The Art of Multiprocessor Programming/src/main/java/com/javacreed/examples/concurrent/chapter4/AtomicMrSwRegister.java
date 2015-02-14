package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.appendix.a.ThreadID;
import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.12")
public class AtomicMrSwRegister<T> implements Register<T> {

  protected final ThreadLocal<Long> lastStamp;

  /** Each entry is SRSW atomic */
  protected final StampedValue<T>[][] table;

  @SuppressWarnings("unchecked")
  public AtomicMrSwRegister(final T init, final int readers) {
    lastStamp = new ThreadLocal<Long>() {
      @Override
      protected Long initialValue() {
        return 0L;
      }
    };

    table = new StampedValue[readers][readers];
    final StampedValue<T> value = new StampedValue<T>(init);
    for (int i = 0; i < readers; i++) {
      for (int j = 0; j < readers; j++) {
        table[i][j] = value;
      }
    }
  }

  @Override
  public T read() {
    final int me = ThreadID.get();
    StampedValue<T> value = table[me][me];
    for (int i = 0; i < table.length; i++) {
      value = StampedValue.max(value, table[i][me]);
    }

    for (int i = 0; i < table.length; i++) {
      if (i == me) {
        continue;
      }

      table[me][i] = value;
    }
    return value.getValue();
  }

  @Override
  public void write(final T v) {
    final long stamp = lastStamp.get() + 1;
    lastStamp.set(stamp);
    final StampedValue<T> value = new StampedValue<>(stamp, v);
    for (int i = 0; i < table.length; i++) {
      table[i][i] = value;
    }
  }

}
