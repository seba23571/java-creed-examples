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

import java.io.ByteArrayInputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Albert
 */
public class ReadableCsvReaderTest {

  @Test
  public void testBasicValues() throws Exception {
    final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(
        "hello,world\nhello,java,world\n".getBytes("UTF-8")), "UTF-8").build();

    // First Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertTrue(reader.hasMoreLines());

    // Second Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("java", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertFalse(reader.hasMoreLines());
  }

  @Test
  public void testEscapedValues() throws Exception {
    final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(
        "hello,\\\"world\\\"\nhello\\n,java\\r\\n\\,world\n".getBytes("UTF-8")), "UTF-8").build();

    // First Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("\"world\"", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertTrue(reader.hasMoreLines());

    // Second Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello\n", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("java\r\n,world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertFalse(reader.hasMoreLines());
  }

  @Test
  public void testLineSeparators() throws Exception {
    final Set<String> lineSeparatos = new LinkedHashSet<>();
    lineSeparatos.add("\n");
    lineSeparatos.add("\r");
    lineSeparatos.add("\r\n");

    for (final String lineSeparator : lineSeparatos) {
      final String sample = "hello,world" + lineSeparator + "hello,java,world" + lineSeparator;
      final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(sample.getBytes("UTF-8")),
          "UTF-8").build();

      // First Line
      reader.beginLine();
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("hello", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("world", reader.readValue());
      Assert.assertFalse(reader.hasMoreValues());
      reader.endLine();
      Assert.assertTrue(reader.hasMoreLines());

      // Second Line
      reader.beginLine();
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("hello", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("java", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("world", reader.readValue());
      Assert.assertFalse(reader.hasMoreValues());
      reader.endLine();
      Assert.assertFalse(reader.hasMoreLines());
    }
  }

  @Test
  public void testSpecialSeparatorValues() throws Exception {
    final Set<String> valueSeparatos = new LinkedHashSet<>();
    valueSeparatos.add("||");
    valueSeparatos.add("诶"); // Chinese A
    valueSeparatos.add("Ā");

    for (final String valueSeparator : valueSeparatos) {
      final String sample = "hello" + valueSeparator + "world\nhello" + valueSeparator + "java" + valueSeparator
          + "world\n";
      final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(sample.getBytes("UTF-8")),
          "UTF-8").setValueSeparator(valueSeparator).build();

      // First Line
      reader.beginLine();
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("hello", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("world", reader.readValue());
      Assert.assertFalse(reader.hasMoreValues());
      reader.endLine();
      Assert.assertTrue(reader.hasMoreLines());

      // Second Line
      reader.beginLine();
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("hello", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("java", reader.readValue());
      Assert.assertTrue(reader.hasMoreValues());
      Assert.assertEquals("world", reader.readValue());
      Assert.assertFalse(reader.hasMoreValues());
      reader.endLine();
      Assert.assertFalse(reader.hasMoreLines());
    }
  }

  @Test
  public void testSplitEndOfLineToken() throws Exception {
    final CsvReader reader = new ReadableCsvReader.Builder(
        new ByteArrayInputStream("a,b\r\nc,d\r\n".getBytes("UTF-8")), "UTF-8").setBufferSize(4).build();

    // First Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("a", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("b", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertTrue(reader.hasMoreLines());

    // Second Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("c", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("d", reader.readValue());
    reader.endLine();
    Assert.assertFalse(reader.hasMoreLines());
  }

  @Test(expected = IllegalStateException.class)
  public void testValueBoundaryMissing() throws Exception {
    final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(
        "\"hello\",world\n\"hello,java,world\n".getBytes("UTF-8")), "UTF-8").build();

    // First Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertTrue(reader.hasMoreLines());

    // Second Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello, java", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertFalse(reader.hasMoreLines());
  }

  @Test
  public void testValuesWithBoundaries() throws Exception {
    final CsvReader reader = new ReadableCsvReader.Builder(new ByteArrayInputStream(
        "\"hello\",world\n\"hello, java\",world\n".getBytes("UTF-8")), "UTF-8").build();

    // First Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertTrue(reader.hasMoreLines());

    // Second Line
    reader.beginLine();
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("hello, java", reader.readValue());
    Assert.assertTrue(reader.hasMoreValues());
    Assert.assertEquals("world", reader.readValue());
    Assert.assertFalse(reader.hasMoreValues());
    reader.endLine();
    Assert.assertFalse(reader.hasMoreLines());
  }
}
