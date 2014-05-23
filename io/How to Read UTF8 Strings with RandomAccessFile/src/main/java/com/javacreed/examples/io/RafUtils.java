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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class RafUtils {

  public static String lockAndReadFile(final File file, final String encoding, final int bufferSize) throws IOException {
    // The approximate number of bytes required
    final int approxBufferSize = (int) Math.min(Integer.MAX_VALUE, file.length());

    // We need to open this file in read/write mode to be able to lock it
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileLock lock = raf.getChannel().lock();
        final ByteArrayOutputStream out = new ByteArrayOutputStream(approxBufferSize)) {

      final byte[] buffer = new byte[bufferSize];
      for (int length; (length = raf.read(buffer)) != -1;) {
        out.write(buffer, 0, length);
      }

      return new String(out.toByteArray(), encoding);
    }
  }

  private RafUtils() {}

}
