package com.javacreed.examples.sc.part1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  public static void main(final String[] args) {
    final String xmlFile = "META-INF/spring/app-context.xml";
    try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xmlFile)) {

      final Worker worker = context.getBean(Worker.class);
      System.out.println("Worker class: " + worker.getClass().getCanonicalName());
      worker.longTask(1);
      worker.longTask(1);
      worker.longTask(1);
      worker.longTask(2);
      worker.longTask(2);

      // Short task is not cached
      worker.shortTask(1);
      worker.shortTask(1);
    }
  }
}
