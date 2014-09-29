package com.javacreed.examples.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;

public class Example {
  public static void main(final String[] args) throws IOException {
    final File outputFile = new File("output.data");
    try (RandomAccessFile raf = new RandomAccessFile(outputFile, "rw"); FileChannel fc = raf.getChannel();) {
      final FileLock fl = fc.tryLock();
      if (fl == null) {
        // Failed to acquire lock
      } else {
        try (final ReadableByteChannel in = Channels.newChannel(new BufferedInputStream(Example.class
            .getResourceAsStream("/remote.data")))) {
          for (final ByteBuffer buffer = ByteBuffer.allocate(1024); in.read(buffer) != -1;) {
            buffer.flip();
            fc.write(buffer);
            buffer.clear();
          }
        } finally {
          fl.release();
        }
      }
    }
  }
}
