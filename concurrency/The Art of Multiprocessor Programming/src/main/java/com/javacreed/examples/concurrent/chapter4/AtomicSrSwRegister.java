package com.javacreed.examples.concurrent.chapter4;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("4.11")
public class AtomicSrSwRegister<T> implements Register<T> {

  private ThreadLocal<Long> lastStamp;

  private ThreadLocal<StampedValue<T>> lastRead;

  /** Regular SRSW timestamp-value pair */
  private StampedValue<T> value;

  public AtomicSrSwRegister(final T init) {
    value = new StampedValue<T>(init);
    lastStamp = new ThreadLocal<Long>() {
      @Override
      protected Long initialValue() {
        return 0L;
      }
    };

    lastRead = new ThreadLocal<StampedValue<T>>() {
      @Override
      protected StampedValue<T> initialValue() {
        return value;
      }
    };
  }

  @Override
  public T read() {
    final StampedValue<T> value = this.value;
    final StampedValue<T> last = lastRead.get();
    final StampedValue<T> result = StampedValue.max(value, last);
    lastRead.set(result);
    return result.getValue();
  }

  @Override
  public void write(final T v) {
    final long stamp = lastStamp.get() + 1;
    value = new StampedValue<>(stamp, v);
    lastStamp.set(stamp);
  }

}
