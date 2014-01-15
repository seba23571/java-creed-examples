package com.javacreed.examples.deadlock.part5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example4 {

  public static void main(final String[] args) throws Exception {
    // Create a lock and acquire it
    final Lock lock = new ReentrantLock();
    lock.lock();

    try {
      // Create another thread that tries to acquire the same lock
      final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            // Wait a minute before it gives up
            ThreadUtils.log("Acquiring the lock...");
            if (lock.tryLock(1, TimeUnit.MINUTES)) {
              try {
                ThreadUtils.log("Working...");
              } finally {
                lock.unlock();
              }
            }
          } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            ThreadUtils.log("Cancelled");
          }
        }
      }, "Thread-1");
      thread.start();

      // Wait 2 seconds before interrupting the thread
      Thread.sleep(2000);

      // Cancel the thread from what it is doing and wait for it to finish
      thread.interrupt();
      thread.join();
    } finally {
      lock.unlock();
    }

    ThreadUtils.log("Done");
  }
}
