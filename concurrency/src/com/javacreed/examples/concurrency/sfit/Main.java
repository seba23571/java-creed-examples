package com.javacreed.examples.concurrency.sfit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

  public static void main(final String[] args) {
    final ExecutorService executorService = Executors.newFixedThreadPool(5);
    try {
      final long startTime = System.nanoTime();
      final List<Future<?>> list = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        final Future<?> future = executorService.submit(new MyWorker(8));
        list.add(future);
      }

      Util.printLog("Waiting for the workers to finish");
      Main.method1(list, 5, TimeUnit.SECONDS);
      final long finishTime = System.nanoTime();
      Util.printLog("Program finished after: %,d nano seconds", finishTime - startTime);

    } finally {
      executorService.shutdown();
    }
  }

  public static void method1(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
    for (final Future<?> future : list) {
      try {
        future.get(timeout, timeUnit);
      } catch (final TimeoutException e) {
        future.cancel(true);
      } catch (final Exception e) {
        Util.printLog("Failed: %s", e);
      }
    }
  }

  public static void method2(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
    long globalWaitTime = timeUnit.toNanos(timeout);
    for (final Future<?> future : list) {
      final long waitStart = System.nanoTime();
      try {
        future.get(globalWaitTime, TimeUnit.NANOSECONDS);
      } catch (final TimeoutException e) {
        future.cancel(true);
      } catch (final Exception e) {
        Util.printLog("Failed: %s", e);
      } finally {
        final long timeTaken = System.nanoTime() - waitStart;
        globalWaitTime -= timeTaken;
      }
    }
  }

}
