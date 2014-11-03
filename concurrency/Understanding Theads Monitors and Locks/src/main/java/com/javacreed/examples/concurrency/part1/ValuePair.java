/*
 * #%L
 * Understanding Theads Monitors and Locks
 * $Id:$
 * $HeadURL$
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
package com.javacreed.examples.concurrency.part1;

/**
 * This class has two fields, {@link #a} and {@link #b}, which should always have the same value. The value
 * can only be set through one method: {@link #setValue(int)}.
 * 
 * @author Albert
 * 
 */
public class ValuePair {

  private int a;
  private int b;

  public synchronized void copy(final ValuePair other) {
    this.a = other.a;
    this.b = other.b;
  }

  public synchronized void setValue(final int value) {
    this.a = value;
    this.b = value;
  }

  @Override
  public synchronized String toString() {
    return String.format("a: %d and b: %d", a, b);
  }

}
