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

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(final String[] args) {
    final String xmlFile = "META-INF/spring/app-context.xml";
    try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlFile)) {

      final long start = System.nanoTime();
      final Fibonacci sequence = context.getBean("fibonacci", Fibonacci.class);
      final long fibNumber = sequence.valueAt(5);
      final int executions = sequence.getExecutions();
      final long timeTaken = System.nanoTime() - start;
      System.out.printf("The 5th Fibonacci number is: %d (%,d executions in %,d NS)%n", fibNumber, executions,
          timeTaken);
    }
  }
}
