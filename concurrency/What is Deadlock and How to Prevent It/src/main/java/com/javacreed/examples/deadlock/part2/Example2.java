package com.javacreed.examples.deadlock.part2;

import com.javacreed.examples.deadlock.utils.ThreadUtils;

public class Example2 {

  public static void main(final String[] args) throws Exception {

    final Object lockX = new Object();
    final Object lockY = new Object();

    final Thread threadA = new Thread(new Runnable() {
      @Override
      public void run() {
        ThreadUtils.log("Acquire lock-X");
        synchronized (lockX) {
          ThreadUtils.log("Acquire lock-Y");
          synchronized (lockY) {
            ThreadUtils.log("Both locks are acquired");
          }
          ThreadUtils.log("Release lock-Y");
        }
        ThreadUtils.log("Release lock-X");
      }
    }, "Thread-A");
    threadA.start();

    final Thread threadB = new Thread(new Runnable() {
      @Override
      public void run() {
        ThreadUtils.log("Acquire lock-Y");
        synchronized (lockY) {
          ThreadUtils.log("Acquire lock-X");
          synchronized (lockX) {
            ThreadUtils.log("Both locks are acquired");
          }
          ThreadUtils.log("Release lock-X");
        }
        ThreadUtils.log("Release lock-Y");
      }
    }, "Thread-B");
    threadB.start();

    /* Wait for the threads to stop */
    threadA.join();
    threadB.join();
  }
}
