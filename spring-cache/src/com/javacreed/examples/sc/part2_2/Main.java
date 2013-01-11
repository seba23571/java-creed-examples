package com.javacreed.examples.sc.part2_2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(final String[] args) {
    final String xmlFile = "META-INF/spring/app-context.xml";
    try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlFile)) {

      final long start = System.nanoTime();
      final Fibonacci sequence = context.getBean("fibonacci2", Fibonacci.class);
      final long fibNumber = sequence.valueAt(40, sequence);
      final int executions = sequence.getExecutions();
      final long timeTaken = System.nanoTime() - start;
      System.out.printf("The 5th fibonacci number is: %d (%,d executions in %,d NS)%n", fibNumber, executions,
          timeTaken);
    }
  }
}
