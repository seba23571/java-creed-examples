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
package com.javacreed.api.csv.adapter;

import com.javacreed.api.csv.CsvException;

public interface CsvTypeAdapterFactory<T> {

  /**
   * Returns an instance of the {@link CsvTypeAdapter}. Each implementation can either return the same instance of
   * {@link CsvTypeAdapter} or a new copy every time this method is called. The returned type adapter should never be
   * {@code null}.
   * 
   * @return an instance of the {@link CsvTypeAdapter}
   * @throws CsvException
   *           if the factory fails to create the type adapter
   */
  CsvTypeAdapter<T> build() throws CsvException;
}
