package com.javacreed.examples.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Example2 {

    public static void main(final String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            final long startTime = System.nanoTime();
            final List<Future<?>> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                final Future<?> future = executorService.submit(new ExampleWorker(8));
                list.add(future);
            }

            Util.printLog("Waiting for the workers to finish");
            Example2.method2(list, 5, TimeUnit.SECONDS);
            final long finishTime = System.nanoTime();
            Util.printLog("Program finished after: %,d nano seconds", finishTime - startTime);

        } finally {
            executorService.shutdown();
        }
    }

    private static void method2(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
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
                globalWaitTime = Math.max(globalWaitTime - timeTaken, 0);
            }
        }
    }

}
