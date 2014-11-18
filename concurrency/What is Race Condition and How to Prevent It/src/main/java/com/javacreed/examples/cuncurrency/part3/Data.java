/*
 * #%L
 * What is Race Condition and How to Prevent It?
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
package com.javacreed.examples.cuncurrency.part3;

public class Data {

  private int value;

  public synchronized void adjustBy(final int adjustment) {
    value += adjustment;
  }

  public synchronized int getValue() {
    return value;
  }

  public synchronized void setValue(final int value) {
    this.value = value;
  }

}
