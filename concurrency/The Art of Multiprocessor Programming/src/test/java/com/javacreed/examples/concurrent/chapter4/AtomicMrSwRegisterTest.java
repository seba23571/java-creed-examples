package com.javacreed.examples.concurrent.chapter4;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

public class AtomicMrSwRegisterTest {

  @Test
  public void testRead() throws Throwable {
    final AtomicReference<Throwable> exception = new AtomicReference<Throwable>();

    final Register<String> register = new AtomicMrSwRegister<String>("a", 4) {
      @Override
      public void write(final String v) {
        final long stamp = lastStamp.get() + 1;
        lastStamp.set(stamp);
        final StampedValue<String> value = new StampedValue<>(stamp, v);
        for (int i = 0; i < 1; i++) {
          table[i][i] = value;
        }
      }
    };

    final Thread writerThread = new Thread(new Runnable() {
      
      private final Register<String> local = register;
      
      @Override
      public void run() {
        local.write("b");
      }
    });
    writerThread.start();
    writerThread.join();

    final int numberOfReaders = 4;
    final Thread[] readersThreads = new Thread[numberOfReaders];
    for (int i = 0; i < numberOfReaders; i++) {
      readersThreads[i] = new Thread(new Runnable() {
        
        private final Register<String> local = register;

        @Override
        public void run() {
          try {
            Assert.assertEquals("b", local.read());
          } catch (final AssertionError e) {
            exception.compareAndSet(null, e);
          } catch (final Throwable e) {
            exception.compareAndSet(null, e);
          }
        }
      });
    }

    readersThreads[0].start();
    readersThreads[0].join();

    for (int i = 1; i < numberOfReaders; i++) {
      readersThreads[i].start();
    }

    for (int i = 1; i < numberOfReaders; i++) {
      readersThreads[i].join();
    }

    if (exception.get() != null) {
      throw exception.get();
    }
  }
}
