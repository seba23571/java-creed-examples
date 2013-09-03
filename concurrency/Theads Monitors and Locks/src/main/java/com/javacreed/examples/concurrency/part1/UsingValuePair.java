package com.javacreed.examples.concurrency.part1;

public class UsingValuePair {
  public static void main(final String[] args) throws InterruptedException {
    final ValuePair object1 = new ValuePair();
    object1.setValue(1);

    final ValuePair object2 = new ValuePair();
    object2.setValue(5);

    final Thread thread1 = new Thread("Thread 1") {
      @Override
      public void run() {
        object1.copy(object2);
      }
    };

    final Thread thread2 = new Thread("Thread 2") {
      @Override
      public void run() {
        object2.setValue(12);
      }
    };

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
    
    System.out.println(object1);
  }
}
