/*
 * #%L
 * The Art of Multiprocessor Programming
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
package com.javacreed.examples.concurrent.chapter3;

import com.javacreed.examples.concurrent.extra.Figure;

@Figure("3.11")
public class FinalFieldExample {

  @SuppressWarnings("unused")
  public static void reader() {
    if (FinalFieldExample.f != null) {
      final int i = FinalFieldExample.f.x;
      final int j = FinalFieldExample.f.y;
    }
  }

  public static void writer() {
    FinalFieldExample.f = new FinalFieldExample();
  }

  private final int x;

  private final int y;

  private static FinalFieldExample f;

  public FinalFieldExample() {
    x = 3;
    y = 4;
  }
}
