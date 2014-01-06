package com.javacreed.examples.deadlock.part5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example2 {

  public static void main(final String[] args) throws Exception {
    final Lock lock = new ReentrantLock();

    final Thread thread1 = Example2.runInThread(lock, "Thread-1");
    final Thread thread2 = Example2.runInThread(lock, "Thread-2");

    thread1.join();
    thread2.join();

    ThreadUtils.log("Done");
  }

  private static Thread runInThread(final Lock lock, final String name) {
    final Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        if (lock.tryLock()) {
          try {
            ThreadUtils.log("Working...");
            ThreadUtils.silentSleep(100);
          } finally {
            lock.unlock();
          }
        } else {
          ThreadUtils.log("Lock taken.  I'm giving up");
        }
      }
    }, name);
    thread.start();
    return thread;
  }
}
