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
package com.javacreed.examples.sc.part2a;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("fibonacci")
public class Fibonacci {

  private int executions = 0;

  public int getExecutions() {
    return executions;
  }

  public void resetExecutions() {
    this.executions = 0;
  }

  @Cacheable("fibonacci")
  public long valueAt(final long index) {
    executions++;
    if (index < 2) {
      return 1;
    }

    return valueAt(index - 1) + valueAt(index - 2);
  }

}
