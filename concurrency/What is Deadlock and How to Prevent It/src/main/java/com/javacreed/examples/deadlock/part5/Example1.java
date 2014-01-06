package com.javacreed.examples.deadlock.part5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example1 {

  public static void main(final String[] args) throws Exception {

    final Lock lock = new ReentrantLock();
    if (lock.tryLock()) {
      try {
        // Access the resource protected by this lock
      } finally {
        lock.unlock();
      }
    }
  }
}
