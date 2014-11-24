/*
 * #%L
 * Java Fork Join Example
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
package com.javacreed.examples.concurrency.part3;

import java.util.concurrent.TimeUnit;

import com.javacreed.examples.concurrency.utils.FilePath;
import com.javacreed.examples.concurrency.utils.Results;

public class Example4 {

  public static void main(final String[] args) {
    final Results results = new Results();
    for (int i = 0; i < 3; i++) {
      results.startTime();
      final long size = DirSize.sizeOf(FilePath.TEST_DIR);
      final long taken = results.endTime();
      System.out.printf("Size of '%s': %d bytes (in %d nano)%n", FilePath.TEST_DIR, size, taken);
    }

    final long takenInNano = results.getAverageTime();
    System.out.printf("Average: %d nano (%d seconds)%n", takenInNano, TimeUnit.NANOSECONDS.toSeconds(takenInNano));
  }
}
