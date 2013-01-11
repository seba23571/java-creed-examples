package com.javacreed.examples.sc.part2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("fibonacci2")
public class Fibonacci2 {
  
  private int executions = 0;

  public int getExecutions() {
    return executions;
  }

  @Cacheable("fibonacci")
  public long valueAt(final long index, Fibonacci2 callback) {
    executions++;
    if (index < 2) {
      return 1;
    }

    return callback.valueAt(index - 1, callback) + callback.valueAt(index - 2, callback);
  }

  public void resetExecutions() {
    this.executions = 0;
  }

}
