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
package com.javacreed.examples.sc.part1;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Worker {

  @Cacheable("task")
  public String longTask(final long id) {
    System.out.printf("Running long task for id: %d...%n", id);
    return "Long task for id " + id + " is done";
  }

  public String shortTask(final long id) {
    System.out.printf("Running short task for id: %d...%n", id);
    return "Short task for id " + id + " is done";
  }

}
