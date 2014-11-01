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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class AppendableCsvWriter implements CsvWriter {

  public static class Builder extends Configurable {

    private final Appendable appendable;

    public Builder(final Appendable appendable) {
      this.appendable = Objects.requireNonNull(appendable);
    }

    public AppendableCsvWriter build() {
      return new AppendableCsvWriter(appendable,//@formatter:off 
                    values.get(Configurable.Type.VALUE_SEPARATOR), 
                    values.get(Configurable.Type.LINE_SEPARATOR), 
                    values.get(Configurable.Type.VALUE_BOUNDARIES),
                    values.get(Configurable.Type.NULL_EQUIVALENT), 
                    values.get(Configurable.Type.ESCAPE_CHARACTER), 
                    strategy);//@formatter:on
    }

    public Builder setDynamicNumberOfColumns() {
      strategy = DynamicNumberOfLinesStrategy.INSTANCE;
      return this;
    }

    public Builder setFixedNumberOfColumns(final int numberOfColumns) throws IllegalArgumentException {
      strategy = new FixedNumberOfLinesStrategy(numberOfColumns);
      return this;
    }

    public Builder setLineSeparator(final String lineSeparator) {
      setValue(Configurable.Type.LINE_SEPARATOR, lineSeparator);
      return this;
    }

    public Builder setNullEquivalent(final String nullEquivalent) {
      setValue(Configurable.Type.NULL_EQUIVALENT, nullEquivalent);
      return this;
    }

    public Builder setValueBoundaries(final String valueBounderies) {
      setValue(Configurable.Type.VALUE_BOUNDARIES, valueBounderies);
      return this;
    }

    public Builder setValueSeparator(final String valueSeparator) {
      setValue(Configurable.Type.VALUE_SEPARATOR, valueSeparator);
      return this;
    }

    public Builder setWriterValidationStrategy(final WriterStrategy strategy) throws NullPointerException {
      this.strategy = Objects.requireNonNull(strategy);
      return this;
    }

    public Builder useOsDefaultLineSeperator() {
      setValue(Configurable.Type.LINE_SEPARATOR, System.lineSeparator());
      return this;
    }
  }

  private static class Configurable {

    private enum Type {
      /** */
      VALUE_SEPARATOR("Values Separator", ",") {
        @Override
        public boolean accept(final String value) {
          return !LINE_SEPARATOR.accept(value);
        }
      },

      /** */
      LINE_SEPARATOR("Line Separator", "\n") {
        @Override
        public boolean accept(final String value) {
          switch (value) {
          case "\r":
          case "\n":
          case "\r\n":
            return true;
          }
          return false;
        }
      },

      /** */
      VALUE_BOUNDARIES("Value Boundaries", "\"") {
        @Override
        public boolean accept(final String value) {
          switch (value) {
          case "\"":
          case "\'":
            return true;
          }
          return false;
        }
      },

      /** */
      NULL_EQUIVALENT("Null Equivalent", "{NULL}"),

      /** */
      ESCAPE_CHARACTER("Escape Character", "\\");

      private final String label;
      private final String defaultValue;

      private Type(final String label, final String defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;
      }

      public boolean accept(final String value) {
        return true;
      }

      public String getDefaultValue() {
        return defaultValue;
      }

      @Override
      public String toString() {
        return label;
      }
    }

    protected final Map<Type, String> values = new HashMap<>();

    protected WriterStrategy strategy = DynamicNumberOfLinesStrategy.INSTANCE;

    protected Configurable() {
      // Set the defaults
      useDefaultsTypes();
    }

    protected void setValue(final Type type, final String value) throws NullPointerException, IllegalArgumentException {
      if (value == null) {
        throw new NullPointerException("The value cannot be null");
      }

      if (value.length() == 0) {
        throw new IllegalArgumentException("The value cannot be blank (empty string)");
      }

      if (!type.accept(value)) {
        throw new IllegalArgumentException("The value '" + value + "' cannot be used as " + type);
      }

      // Make sure that the new value does not conflict with the existing ones
      for (final Entry<Type, String> entry : values.entrySet()) {
        if (entry.getKey() != type) {
          if (entry.getValue().equals(value)) {
            throw new IllegalArgumentException("The value '" + value + "' is already used as " + entry.getKey());
          }
        }
      }

      // Set the value
      values.put(type, value);
    }

    protected void useDefaultsTypes() {
      for (final Type type : Type.values()) {
        values.put(type, type.getDefaultValue());
      }
    }

  }

  public static class Factory extends Configurable implements CsvWriterFactory {

    @Override
    public CsvWriter create(final Appendable appendable) {
      final Builder builder = new Builder(appendable);
      builder.strategy = strategy;
      builder.values.putAll(values);
      return builder.build();
    }

    @Override
    public void setValueSeparator(final String valueSeparator) {
      setValue(Configurable.Type.VALUE_SEPARATOR, valueSeparator);
    }

  }

  private static enum State {
    INVALID, BEGIN_LINE, IN_LINE, END_LINE
  }

  private final Appendable out;
  private final String valueSeparator;
  private final String lineSeparator;
  private final String valueBounderies;
  private final String nullEquivalent;

  private final WriterStrategy strategy;

  private final Map<String, String> toEscape;

  private State state = State.END_LINE;

  private AppendableCsvWriter(final Appendable out, final String valueSeparator, final String lineSeparator,
      final String valueBounderies, final String nullEquivalent, final String escapeCharacter,
      final WriterStrategy strategy) {
    this.out = out;
    this.valueSeparator = valueSeparator;
    this.lineSeparator = lineSeparator;
    this.valueBounderies = valueBounderies;
    this.nullEquivalent = nullEquivalent;
    this.strategy = strategy;

    final Map<String, String> map = new HashMap<>();
    for (final String key : new String[] { escapeCharacter, lineSeparator, valueBounderies }) {
      map.put(key, escapeCharacter + key);
    }
    map.put("\t", escapeCharacter + "t");
    map.put("\b", escapeCharacter + "b");
    map.put("\n", escapeCharacter + "n");
    map.put("\r", escapeCharacter + "r");
    map.put("\f", escapeCharacter + "f");
    map.put("\'", escapeCharacter + "'");
    map.put("\"", escapeCharacter + "\"");
    map.put("\\", escapeCharacter + "\\");
    toEscape = Collections.unmodifiableMap(map);
  }

  @Override
  public void beginLine() throws IllegalStateException {
    switch (state) {
    case END_LINE:
      state = State.BEGIN_LINE;
      break;
    case INVALID:
      throw new IllegalStateException("CSV Writer is in an invalid state");
    default:
      throw new IllegalStateException("Cannot begin line before ending the previous one");
    }
  }

  @Override
  public void endLine() throws IllegalStateException, IOException {
    switch (state) {
    case BEGIN_LINE:
    case IN_LINE:
      strategy.onBeforeEndLine();
      state = State.END_LINE;
      out.append(lineSeparator);
      break;
    case INVALID:
      throw new IllegalStateException("CSV Writer is in an invalid state");
    default:
      throw new IllegalStateException("Cannot end line before begining it");
    }
  }

  private String toCsv(final String text) {
    if (text == null) {
      return nullEquivalent;
    }

    boolean useValueBoundaries = false;

    final StringBuilder builder = new StringBuilder(text.length());
    outerLoop: for (int i = 0, size = text.length(); i < size;) {
      // Use the boundaries if the text contains the valueSeperator
      if (text.startsWith(valueSeparator, i)) {
        useValueBoundaries = true;
        builder.append(valueSeparator);
        i += valueSeparator.length();
        continue outerLoop;
      }

      for (final Entry<String, String> entry : toEscape.entrySet()) {
        final String escape = entry.getKey();
        final String replacement = entry.getValue();
        if (text.startsWith(escape, i)) {
          builder.append(replacement);
          i += escape.length();
          continue outerLoop;
        }
      }

      builder.append(text.charAt(i));
      i++;
    }

    if (useValueBoundaries) {
      builder.insert(0, valueBounderies);
      builder.append(valueBounderies);
    }

    return builder.toString();
  }

  @Override
  public void writeValue(final String value) throws IllegalStateException, IOException {
    switch (state) {
    case IN_LINE:
      out.append(valueSeparator);
    case BEGIN_LINE:
      strategy.onBeforeWriteValue();
      out.append(toCsv(value));
      state = State.IN_LINE;
      break;
    case INVALID:
      throw new IllegalStateException("CSV Writer is in an invalid state");
    default:
      throw new IllegalStateException("Cannot write value before begining a line");
    }
  }

}
