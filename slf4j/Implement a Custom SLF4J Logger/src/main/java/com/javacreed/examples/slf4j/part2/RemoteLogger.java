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
package com.javacreed.examples.slf4j.part2;

import org.slf4j.helpers.MarkerIgnoringBase;

public class RemoteLogger extends MarkerIgnoringBase {

  private static final long serialVersionUID = 7530867510835806088L;

  public RemoteLogger(final String name) {
    this.name = name;
  }

  @Override
  public void debug(final String msg) {
    try {
      Thread.sleep(0);
      System.out.printf("[%s] %s%n", Thread.currentThread().getName(), msg);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    throw new RuntimeException();
  }

  @Override
  public void debug(final String format, final Object arg) {
  }

  @Override
  public void debug(final String format, final Object... arguments) {
  }

  @Override
  public void debug(final String format, final Object arg1, final Object arg2) {
  }

  @Override
  public void debug(final String msg, final Throwable t) {
  }

  @Override
  public void error(final String msg) {
  }

  @Override
  public void error(final String format, final Object arg) {
  }

  @Override
  public void error(final String format, final Object... arguments) {
  }

  @Override
  public void error(final String format, final Object arg1, final Object arg2) {
  }

  @Override
  public void error(final String msg, final Throwable t) {
  }

  @Override
  public void info(final String msg) {
  }

  @Override
  public void info(final String format, final Object arg) {
  }

  @Override
  public void info(final String format, final Object... arguments) {
  }

  @Override
  public void info(final String format, final Object arg1, final Object arg2) {
  }

  @Override
  public void info(final String msg, final Throwable t) {
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public boolean isErrorEnabled() {
    return true;
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public boolean isTraceEnabled() {
    return true;
  }

  @Override
  public boolean isWarnEnabled() {
    return true;
  }

  @Override
  public void trace(final String msg) {
  }

  @Override
  public void trace(final String format, final Object arg) {
  }

  @Override
  public void trace(final String format, final Object... arguments) {
  }

  @Override
  public void trace(final String format, final Object arg1, final Object arg2) {
  }

  @Override
  public void trace(final String msg, final Throwable t) {
  }

  @Override
  public void warn(final String msg) {
  }

  @Override
  public void warn(final String format, final Object arg) {
  }

  @Override
  public void warn(final String format, final Object... arguments) {
  }

  @Override
  public void warn(final String format, final Object arg1, final Object arg2) {
  }

  @Override
  public void warn(final String msg, final Throwable t) {
  }

}
