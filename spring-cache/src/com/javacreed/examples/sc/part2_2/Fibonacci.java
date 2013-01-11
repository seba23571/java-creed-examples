package com.javacreed.examples.sc.part2_2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("fibonacci2")
public class Fibonacci {

  private int executions = 0;

  public int getExecutions() {
    return executions;
  }

  public void resetExecutions() {
    this.executions = 0;
  }

  @Cacheable("fibonacci")
  public long valueAt(final long index, final Fibonacci callback) {
    executions++;
    if (index < 2) {
      return 1;
    }

    return callback.valueAt(index - 1, callback) + callback.valueAt(index - 2, callback);
  }

}
