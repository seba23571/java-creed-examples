/*
 * #%L
 * JavaCreed CSV API
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2014 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.api.csv.stream;

import java.io.IOException;
import java.nio.CharBuffer;

public class CharBufferUtils {

  public static int fill(final CharBuffer charBuffer, final Readable in) throws IOException {
    /* Cannot use mark as this is used by the read method */
    final int position = charBuffer.position();
    charBuffer.position(charBuffer.limit());
    final int read = CharBufferUtils.read(charBuffer, in);
    charBuffer.position(position);
    return read;
  }

  public static int read(final CharBuffer charBuffer, final Readable in) throws IOException {
    /*
     * Update the buffers to accommodate as much as possible. This should be done before the mark as otherwise the mark
     * may be lost (if the buffer was read until the limit the mark will be lost).
     */
    charBuffer.limit(charBuffer.capacity());
    charBuffer.mark();
    final int read = in.read(charBuffer);
    charBuffer.reset();
    /* Set the limit to cover only the bytes read ignoring any empty spaces */
    charBuffer.limit(charBuffer.position() + Math.max(0, read));
    return read;
  }

  public static String shift(final CharBuffer charBuffer) throws IllegalArgumentException {
    return CharBufferUtils.shift(charBuffer, 1);
  }

  public static String shift(final CharBuffer charBuffer, final int offset) throws IllegalArgumentException {
    if (offset < 1) {
      throw new IllegalArgumentException("The offset must be between 1 and the buffer's length both inclusive");
    }

    charBuffer.rewind();
    final int limit = charBuffer.limit();
    if (offset > limit) {
      throw new IllegalArgumentException("The offset of " + offset + " is larger than the buffer's limit of " + limit);
    }

    final char[] temp = new char[limit];
    charBuffer.get(temp);
    final String shiftedChars = new String(temp, 0, offset);

    for (int i = offset; i < temp.length; i++) {
      temp[i - offset] = temp[i];
    }
    for (int i = temp.length - offset; i < temp.length; i++) {
      temp[i] = 0;
    }

    charBuffer.clear();
    charBuffer.put(temp);
    charBuffer.position(limit - offset);

    return shiftedChars;
  }

  public static String shiftRead(final CharBuffer charBuffer, final int length, final Readable in) throws IOException {
    final String shiftedChars = charBuffer.limit() == 0 ? null : CharBufferUtils.shift(charBuffer, length);
    CharBufferUtils.read(charBuffer, in);
    return shiftedChars;
  }

  public static String shiftRead(final CharBuffer charBuffer, final Readable in) throws IOException {
    return CharBufferUtils.shiftRead(charBuffer, 1, in);
  }

  public static String shiftReadRewind(final CharBuffer charBuffer, final int length, final Readable in)
      throws IOException {
    final String shiftedChars = CharBufferUtils.shiftRead(charBuffer, length, in);
    charBuffer.rewind();
    return shiftedChars;
  }

  public static String shiftReadRewind(final CharBuffer charBuffer, final Readable in) throws IOException {
    return CharBufferUtils.shiftReadRewind(charBuffer, 1, in);
  }

  public static Boolean startsWith(final CharBuffer charBuffer, final String token) {
    charBuffer.mark();
    try {
      for (int i = 0; i < token.length(); i++) {
        if (!charBuffer.hasRemaining()) {
          return false;
        }

        final char cb = charBuffer.get();
        final char ct = token.charAt(i);
        if (cb != ct) {
          return false;
        }
      }

      return true;
    } finally {
      charBuffer.reset();
    }
  }

  private CharBufferUtils() {}
}
