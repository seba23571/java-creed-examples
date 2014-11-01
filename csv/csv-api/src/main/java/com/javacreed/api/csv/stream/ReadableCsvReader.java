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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadableCsvReader implements CsvReader {

  public static class Builder extends Configurable {

    private final Readable in;

    public Builder(final InputStream inputStream, final Charset encoding) {
      this(new BufferedReader(new InputStreamReader(inputStream, encoding)));
    }

    public Builder(final InputStream inputStream, final String encoding) throws UnsupportedEncodingException {
      this(new BufferedReader(new InputStreamReader(inputStream, encoding)));
    }

    public Builder(final Readable readable) {
      this.in = Objects.requireNonNull(readable);

      // Set the defaults
      for (final Configurable.Type type : Configurable.Type.values()) {
        values.put(type, type.getDefaultValue());
      }
    }

    public ReadableCsvReader build() {
      return new ReadableCsvReader(in, bufferSize, values.get(Configurable.Type.VALUE_SEPARATOR));
    }

    @Override
    public Builder setBufferSize(final int bufferSize) throws IllegalArgumentException {
      super.setBufferSize(bufferSize);
      return this;
    }

    public Builder setValueSeparator(final String valueSeparator) {
      setValue(Configurable.Type.VALUE_SEPARATOR, valueSeparator);
      return this;
    }

  }

  public static interface Command<T> {

    T execute() throws IllegalStateException, IOException;

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
        return defaultValue.equals(value);
      }

      public String getDefaultValue() {
        return defaultValue;
      }

      @Override
      public String toString() {
        return label;
      }
    };

    protected int bufferSize = 1024;
    protected final Map<Configurable.Type, String> values = new HashMap<>();

    protected Object setBufferSize(final int bufferSize) throws IllegalArgumentException {
      if (bufferSize < 2) {
        throw new IllegalArgumentException("");
      }

      this.bufferSize = bufferSize;
      return this;
    }

    protected void setValue(final Configurable.Type type, final String value) throws NullPointerException,
        IllegalArgumentException {
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
      for (final Entry<Configurable.Type, String> entry : values.entrySet()) {
        if (entry.getKey() != type) {
          if (entry.getValue().equals(value)) {
            throw new IllegalArgumentException("The value '" + value + "' is already used as " + entry.getKey());
          }
        }
      }

      // Set the value
      values.put(type, value);
    }

  }

  public static class Factory extends Configurable implements CsvReaderFactory {
    @Override
    public CsvReader create(final Readable readable) {
      final Builder builder = new Builder(readable);
      builder.bufferSize = bufferSize;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(ReadableCsvReader.class);

  private final Readable in;
  private final CharBuffer charBuffer;

  private final String valueSeparator;
  private final String valueBounderies;
  private final String escapeCharacter;
  private final Set<String> lineSeparators;
  private final Set<String> valueSeparators;

  private State state = State.END_LINE;

  private Throwable lastCause;

  private final Map<String, String> toRevert;

  private ReadableCsvReader(final Readable in, final int bufferSize, final String valueSeparator) {
    this.in = in;
    charBuffer = CharBuffer.allocate(bufferSize);
    charBuffer.limit(0);

    this.valueSeparator = valueSeparator;
    this.valueBounderies = "\"";
    this.escapeCharacter = "\\";

    lineSeparators = new LinkedHashSet<>();
    lineSeparators.add("\r\n");
    lineSeparators.add("\n");
    lineSeparators.add("\r");

    valueSeparators = new LinkedHashSet<>();
    valueSeparators.add(valueSeparator);
    valueSeparators.addAll(lineSeparators);

    final Map<String, String> map = new LinkedHashMap<>();
    map.put(escapeCharacter + "t", "\t");
    map.put(escapeCharacter + "b", "\b");
    map.put(escapeCharacter + "n", "\n");
    map.put(escapeCharacter + "r", "\r");
    map.put(escapeCharacter + "f", "\f");
    map.put(escapeCharacter + "'", "\'");
    map.put(escapeCharacter + "\"", "\"");
    map.put(escapeCharacter + "\\", "\\");
    toRevert = Collections.unmodifiableMap(map);
  }

  @Override
  public void beginLine() throws IllegalStateException, IOException {
    execute(new Command<Void>() {
      @Override
      public Void execute() throws IllegalStateException, IOException {
        switch (state) {
        case END_LINE:
          // Fill the buffer
          CharBufferUtils.fill(charBuffer, in);
          ReadableCsvReader.LOGGER.trace("Begin line: '{}' (Position: {} and Limit: {})", new Object[] { charBuffer,
              charBuffer.position(), charBuffer.limit() });
          state = State.BEGIN_LINE;
          break;
        default:
          throw new IllegalStateException("Cannot begin line before ending the previous one");
        }
        return null;
      }
    });
  }

  @Override
  public void endLine() throws IllegalStateException, IOException {
    execute(new Command<Void>() {
      @Override
      public Void execute() throws IllegalStateException, IOException {
        switch (state) {
        case BEGIN_LINE:
        case IN_LINE:
          checkBlock: {
            for (final String endOfLineToken : lineSeparators) {
              if (CharBufferUtils.startsWith(charBuffer, endOfLineToken)) {
                CharBufferUtils.shiftReadRewind(charBuffer, endOfLineToken.length(), in);
                break checkBlock;
              }
            }
            throw new IllegalStateException("Invalid end of line");
          }
          state = State.END_LINE;
          return null;
        default:
          throw new IllegalStateException("Cannot end line before begining it");
        }
      }
    });
  }

  private <T> T execute(final Command<T> command) throws IllegalStateException, IOException {
    if (state == State.INVALID) {
      throw new IllegalStateException("CSV Reader is in an invalid state", lastCause);
    }

    try {
      return command.execute();
    } catch (final RuntimeException e) {
      state = State.INVALID;
      lastCause = e;
      throw e;
    }
  }

  @Override
  public boolean hasMoreLines() throws IllegalStateException, IOException {
    return execute(new Command<Boolean>() {
      @Override
      public Boolean execute() throws IllegalStateException, IOException {
        return charBuffer.hasRemaining() || CharBufferUtils.fill(charBuffer, in) != -1;
      }
    });
  }

  @Override
  public boolean hasMoreValues() throws IllegalStateException, IOException {
    return execute(new Command<Boolean>() {
      @Override
      public Boolean execute() throws IllegalStateException, IOException {
        switch (state) {
        case BEGIN_LINE:
        case IN_LINE:
          if (charBuffer.hasRemaining() || CharBufferUtils.fill(charBuffer, in) != -1) {
            for (final String endOfLineToken : lineSeparators) {
              if (CharBufferUtils.startsWith(charBuffer, endOfLineToken)) {
                return false;
              }
            }
            return true;
          }

          return false;
        default:
          throw new IllegalStateException("Cannot verify whether more values are available before starting a line");
        }
      }
    });
  }

  @Override
  public String readValue() throws IllegalStateException, IOException {
    return execute(new Command<String>() {
      @Override
      public String execute() throws IllegalStateException, IOException {
        switch (state) {
        case IN_LINE:
          if (CharBufferUtils.startsWith(charBuffer, valueSeparator)) {
            CharBufferUtils.shiftReadRewind(charBuffer, valueSeparator.length(), in);
          } else {
            throw new IllegalStateException("Value seperator missing");
          }
        case BEGIN_LINE:

          final boolean hasBounderies = CharBufferUtils.startsWith(charBuffer, valueBounderies);
          if (hasBounderies) {
            ReadableCsvReader.LOGGER.trace("Value has bounderies");
            CharBufferUtils.shiftReadRewind(charBuffer, valueBounderies.length(), in);
          }

          boolean escapeNext = false;
          final StringBuilder valueBuffer = new StringBuilder();
          outerLoop: while (true) {
            if (escapeNext) {
              escapeNext = false;

              final String shifted = CharBufferUtils.shiftReadRewind(charBuffer, 2, in);
              ReadableCsvReader.LOGGER.trace("Read char: '{}'. Buffer: '{}' (Position: {} and Limit: {})", shifted,
                  charBuffer, charBuffer.position(), charBuffer.limit());

              String value = toRevert.get(shifted);
              if (value == null) {
                value = shifted.substring(escapeCharacter.length());
              }
              valueBuffer.append(value);
            } else {
              if (CharBufferUtils.startsWith(charBuffer, escapeCharacter)) {
                escapeNext = true;
                continue;
              }

              if (hasBounderies) {
                // Fail if the end is reached. TODO may be we need to test for when the stream is not ready to return
                // more, but it has not yet been closed.
                if (!charBuffer.hasRemaining()) {
                  throw new IllegalStateException("Value boundary missing");
                }

                if (CharBufferUtils.startsWith(charBuffer, valueBounderies)) {
                  CharBufferUtils.shiftReadRewind(charBuffer, valueBounderies.length(), in);
                  break;
                }
              } else {
                // Fail if the end is reached. TODO may be we need to test for when the stream is not ready to return
                // more, but it has not yet been closed.
                if (!charBuffer.hasRemaining()) {
                  throw new IllegalStateException("Value seperator missing");
                }

                for (final String endOfValueToken : valueSeparators) {
                  if (CharBufferUtils.startsWith(charBuffer, endOfValueToken)) {
                    break outerLoop;
                  }
                }
              }
              final String shifted = CharBufferUtils.shiftReadRewind(charBuffer, in);
              ReadableCsvReader.LOGGER.trace("Read char: '{}'. Buffer: '{}' (Position: {} and Limit: {})", shifted,
                  charBuffer, charBuffer.position(), charBuffer.limit());
              valueBuffer.append(shifted);
            }
          }

          state = State.IN_LINE;
          return valueBuffer.toString();
        default:
          throw new IllegalStateException("Cannot write value before begining a line");
        }
      }
    });
  }
}
