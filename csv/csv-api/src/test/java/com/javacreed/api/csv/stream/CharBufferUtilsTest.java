/*
 * #%L
 * CSV API
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
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;

public class CharBufferUtilsTest {

  @Test
  public void testRead() throws Exception {
    final CharBuffer buffer = CharBuffer.allocate(12);
    Assert.assertEquals(4, CharBufferUtils.read(buffer, new StringReader("0123")));
    Assert.assertEquals(4, buffer.limit());
    Assert.assertEquals(0, buffer.position());
    Assert.assertEquals('0', buffer.get());
    Assert.assertEquals('1', buffer.get());
    Assert.assertEquals('2', buffer.get());
    Assert.assertEquals('3', buffer.get());
    Assert.assertFalse(buffer.hasRemaining());

    /* Read 4 more chars */
    Assert.assertEquals(4, CharBufferUtils.read(buffer, new StringReader("4567")));
    Assert.assertEquals(8, buffer.limit());
    Assert.assertEquals(4, buffer.position());
    Assert.assertEquals('4', buffer.get());
    Assert.assertEquals('5', buffer.get());
    Assert.assertTrue(buffer.hasRemaining());

    /* Read 6 more chars. Note that E and F will not be read. Furthermore, chars 6 and 7 are replaced */
    Assert.assertEquals(6, CharBufferUtils.read(buffer, new StringReader("89ABCDEF")));
    Assert.assertEquals(12, buffer.limit());
    Assert.assertEquals(6, buffer.position());
    Assert.assertEquals('8', buffer.get());
    Assert.assertEquals('9', buffer.get());
    Assert.assertEquals('A', buffer.get());
    Assert.assertEquals('B', buffer.get());
    Assert.assertEquals('C', buffer.get());
    Assert.assertEquals('D', buffer.get());
    Assert.assertFalse(buffer.hasRemaining());

    /*
     * Make sure that the buffer is full and contains the following sequence. This is a redundant test as all these
     * criteria were covered by the previous tests.
     */
    buffer.rewind();
    Assert.assertEquals("01234589ABCD", buffer.toString());

    /*
     * With the buffer full, read a small sample of strings and verify that the buffer limit is readjusted such that it
     * shows only these three letters.
     */
    Assert.assertEquals(3, CharBufferUtils.read(buffer, new StringReader("XYZ")));
    Assert.assertEquals(3, buffer.limit());
    Assert.assertEquals(0, buffer.position());
    Assert.assertEquals("XYZ", buffer.toString());
  }

  @Test
  public void testReadEof() throws Exception {
    final CharBuffer buffer = CharBuffer.allocate(12);
    Assert.assertEquals(4, CharBufferUtils.read(buffer, new StringReader("0123")));
    buffer.position(2);

    // Simulate end of file
    final IMocksControl mockMaker = EasyMock.createStrictControl();
    final Readable in = mockMaker.createMock(Readable.class);
    EasyMock.expect(in.read(EasyMock.anyObject(CharBuffer.class))).andReturn(-1);
    mockMaker.replay();

    Assert.assertEquals(-1, CharBufferUtils.read(buffer, in));
    Assert.assertEquals(2, buffer.position());
    Assert.assertFalse(buffer.hasRemaining());

    buffer.rewind();
    Assert.assertEquals("01", buffer.toString());
  }

  @Test
  public void testShiftAndRead() throws IOException {
    final String input = "hello java world";
    final StringReader reader = new StringReader(input);
    final CharBuffer buffer = CharBuffer.allocate(4);
    Assert.assertEquals(4, CharBufferUtils.read(buffer, reader));

    final StringBuilder builder = new StringBuilder(input.length());
    for (String s; (s = CharBufferUtils.shiftRead(buffer, reader)) != null;) {
      builder.append(s);
    }
    Assert.assertEquals(input, builder.toString());
  }

  @Test
  public void testShiftWithDifferentOffsets() {
    final String input = "0123456789ABCDEF";
    for (int i = 2; i <= input.length(); i++) {
      final CharBuffer buffer = CharBuffer.allocate(input.length());
      buffer.append(input);
      Assert.assertEquals(input.substring(0, i), CharBufferUtils.shift(buffer, i));
      Assert.assertEquals(input.length() - i, buffer.position());

      final String append = "xxxxxxxxxxxxxxxx".substring(0, i);
      buffer.append(append);
      buffer.rewind();
      Assert.assertEquals(input.substring(i) + append, buffer.toString());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShiftWithOffsetLargerThanBuffer() {
    final String input = "1234";
    final CharBuffer buffer = CharBuffer.allocate(input.length());
    buffer.append(input);
    CharBufferUtils.shift(buffer, 5);
  }

  @Test
  public void testShiftWithOffsetOfOne() {
    final String input = "1234";
    final CharBuffer buffer = CharBuffer.allocate(input.length());
    buffer.append(input);
    Assert.assertEquals("1", CharBufferUtils.shift(buffer, 1));
    Assert.assertEquals(3, buffer.position());
    buffer.append("5");
    buffer.rewind();
    Assert.assertEquals("2345", buffer.toString());
  }

  @Test
  public void testStartsWith() {
    final CharBuffer buffer = CharBuffer.allocate(16);
    buffer.append("0123456789ABCDEF");
    buffer.position(2);

    final Map<String, Boolean> values = new LinkedHashMap<>();
    values.put("0", false);
    values.put("123", false);
    values.put("345", false);
    values.put("222", false);
    values.put("234", true);
    values.put("23456789ABCDEF", true);
    for (final Entry<String, Boolean> entry : values.entrySet()) {
      Assert.assertEquals(entry.getKey(), entry.getValue(), CharBufferUtils.startsWith(buffer, entry.getKey()));
      Assert.assertEquals(entry.getKey(), 2, buffer.position());
    }
  }
}
