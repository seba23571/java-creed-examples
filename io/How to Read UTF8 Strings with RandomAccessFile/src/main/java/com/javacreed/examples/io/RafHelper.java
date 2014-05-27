/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Objects;

public class RafHelper implements AutoCloseable {

  public static enum Mode {

    /**
     * Open for reading only. Invoking any of the write methods of the resulting object will cause an
     * java.io.IOException to be thrown.
     * 
     * Locks cannot be used in this mode
     */
    READ_ONLY("r"),

    /** Open for reading and writing. If the file does not already exist then an attempt will be made to create it. */
    READ_WRITE("rw"),

    /**
     * Open for reading and writing, as with "rw", and also require that every update to the file's content or metadata
     * be written synchronously to the underlying storage device.
     */
    READ_WRITE_SYNCH_CONT_META("rws"),

    /**
     * Open for reading and writing, as with "rw", and also require that every update to the file's content be written
     * synchronously to the underlying storage device.
     */
    READ_WRITE_SYNCH_CONT("rwd");

    private final String rafMode;

    private Mode(final String mode) {
      this.rafMode = mode;
    }

    public String getRafMode() {
      return rafMode;
    }

  }

  private final File file;
  private final RandomAccessFile raf;
  private FileLock lock;

  public RafHelper(final File file, final Mode mode) throws NullPointerException, IllegalArgumentException,
      FileNotFoundException {
    this.file = Objects.requireNonNull(file);
    this.raf = new RandomAccessFile(file, mode.getRafMode());
  }

  public RafHelper clear() throws IOException {
    raf.setLength(0);
    return this;
  }

  @Override
  public void close() throws Exception {
    releaseQuietly();
    raf.close();
  }

  public RafHelper lock() throws IOException {
    if (lock == null) {
      lock = raf.getChannel().lock();
    }

    return this;
  }

  public String read(final String encoding) throws IOException {
    return read(encoding, 4096);
  }

  public String read(final String encoding, final int bufferSize) throws IOException {
    // The approximate number of bytes required
    final int approxOutputBufferSize = (int) Math.min(Integer.MAX_VALUE, file.length());

    // We need to open this file in read/write mode to be able to lock it
    try (final ByteArrayOutputStream out = new ByteArrayOutputStream(approxOutputBufferSize)) {

      final byte[] buffer = new byte[bufferSize];
      for (int length; (length = raf.read(buffer)) != -1;) {
        out.write(buffer, 0, length);
      }

      return new String(out.toByteArray(), encoding);
    }
  }

  public RafHelper release() throws IOException {
    if (lock != null) {
      lock.release();
    }

    return this;
  }

  public RafHelper releaseQuietly() {
    try {
      release();
    } catch (IOException | RuntimeException e) {}
    return this;
  }
}
