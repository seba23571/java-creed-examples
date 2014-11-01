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
package com.javacreed.api.csv;

public class CsvException extends RuntimeException {

  private static final long serialVersionUID = -1071293310224703314L;

  public static CsvException handle(final String message, final Throwable cause) {
    if (cause instanceof CsvException) {
      return (CsvException) cause;
    }

    return new CsvException(message, cause);
  }

  public CsvException(final String message) {
    super(message);
  }

  public CsvException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CsvException(final Throwable cause) {
    super(cause);
  }

}
