package com.javacreed.examples.concurrency;

import java.util.concurrent.TimeUnit;

public class ExampleWorker implements Runnable {

    private final int sleepTime;

    public ExampleWorker(final int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        final long startTime = System.nanoTime();
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            Util.printLog("Finished");
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            final long interruptedAfter = System.nanoTime() - startTime;
            Util.printLog("Interrupted after %,d nano seconds", interruptedAfter);
        }
    }

}
