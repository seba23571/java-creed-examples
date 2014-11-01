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

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class AppendableCsvWriterTest {

  @Test
  public void testBasicValues() throws Exception {
    final StringWriter out = new StringWriter();
    final CsvWriter writer = new AppendableCsvWriter.Builder(out).build();

    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("world");
    writer.endLine();
    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("java");
    writer.writeValue("world");
    writer.endLine();

    Assert.assertEquals("hello,world\nhello,java,world\n", out.toString());
  }

  @Test
  public void testFixedNumberOfColumns() throws Exception {
    final StringWriter out = new StringWriter();
    final CsvWriter writer = new AppendableCsvWriter.Builder(out).setFixedNumberOfColumns(3).build();

    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("java");
    writer.writeValue("world");
    writer.endLine();
    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("java");
    writer.writeValue("world");
    writer.endLine();

    Assert.assertEquals("hello,java,world\nhello,java,world\n", out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testFixedNumberOfColumnsInvalidLess() throws Exception {
    final CsvWriter writer = new AppendableCsvWriter.Builder(new StringWriter()).setFixedNumberOfColumns(3).build();

    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("java");
    writer.endLine();
  }

  @Test(expected = IllegalStateException.class)
  public void testFixedNumberOfColumnsInvalidMore() throws Exception {
    final CsvWriter writer = new AppendableCsvWriter.Builder(new StringWriter()).setFixedNumberOfColumns(3).build();

    writer.beginLine();
    writer.writeValue("hello");
    writer.writeValue("world");
    writer.writeValue("of");
    writer.writeValue("java");
    writer.endLine();
  }

  @Test(timeout = 1000)
  public void testSpecialValues() throws Exception {
    final Set<String> valuesSeparatorsSet = new LinkedHashSet<>();
    valuesSeparatorsSet.add(",");
    valuesSeparatorsSet.add("\t");
    valuesSeparatorsSet.add("|");
    valuesSeparatorsSet.add("ABC");

    for (final String valueSeparator : valuesSeparatorsSet) {
      final Map<String, String> valuesMap = new LinkedHashMap<>();
      valuesMap.put("hello world", "hello world");
      valuesMap.put(null, "{NULL}");
      valuesMap.put("\"hello world\"", "\\\"hello world\\\"");
      valuesMap.put("hello\"world\"", "hello\\\"world\\\"");
      valuesMap.put("hello\rworld", "hello\\rworld");
      valuesMap.put("hello\nworld", "hello\\nworld");
      valuesMap.put("hello\\world", "hello\\\\world");
      valuesMap.put("hello" + valueSeparator + "world", "\"hello" + valueSeparator + "world\"");

      for (final Entry<String, String> entry : valuesMap.entrySet()) {
        final StringWriter out = new StringWriter();
        final CsvWriter writer = new AppendableCsvWriter.Builder(out).setValueSeparator(valueSeparator).build();

        writer.beginLine();
        writer.writeValue(entry.getKey());
        writer.writeValue(entry.getKey());
        writer.endLine();

        final String expected = entry.getValue() + valueSeparator + entry.getValue() + "\n";
        final String message = String.format("Separator: '%s'", valueSeparator);
        Assert.assertEquals(message, expected, out.toString());
      }
    }
  }

  @Test
  public void testUnicodeValues() throws Exception {
    final StringWriter out = new StringWriter();
    final CsvWriter writer = new AppendableCsvWriter.Builder(out).build();

    writer.beginLine();
    writer.writeValue("ħēĺļō");
    writer.writeValue("ŵŏrlď");
    writer.endLine();
    writer.beginLine();
    writer.writeValue("ĥėŀłő");
    writer.writeValue("ĵāvă");
    writer.writeValue("woŕlđ");
    writer.endLine();

    Assert.assertEquals("ħēĺļō,ŵŏrlď\nĥėŀłő,ĵāvă,woŕlđ\n", out.toString());
  }
}
