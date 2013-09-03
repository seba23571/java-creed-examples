package com.javacreed.examples.concurrency.part1;

public class CreateSimpleThread {

  private static void log(final String message) {
    System.out.printf("%tT [%s] %s%n", System.currentTimeMillis(), Thread.currentThread().getName(), message);
  }

  public static void main(final String[] args) throws InterruptedException {
    CreateSimpleThread.log("Start");

    // Approach 1
    final Thread t = new Thread("My Thread") {
      @Override
      public void run() {
        CreateSimpleThread.log("Hello from thread");
      }
    };

    // Approach 2
    // final Runnable r = new Runnable() {
    // @Override
    // public void run() {
    // CreateSimpleThread.log("Hello from thread");
    // }
    // };
    // final Thread t = new Thread(r, "My Thread");

    t.start();

    CreateSimpleThread.log("Waiting for thread to finish");
    t.join();

    CreateSimpleThread.log("Done");
  }

}
