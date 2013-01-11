package com.javacreed.examples.sc.part2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("fibonacci")
public class Fibonacci {

  private int executions = 0;

  public int getExecutions() {
    return executions;
  }

  @Cacheable("fibonacci")
  public long valueAt(final long index) {
    executions++;
    if (index < 2) {
      return 1;
    }

    return valueAt(index - 1) + valueAt(index - 2);
  }

  public void resetExecutions() {
    this.executions = 0;
  }

}
