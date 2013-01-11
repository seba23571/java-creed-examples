package com.javacreed.examples.sc.part1;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Worker {

  @Cacheable("task")
  public String longTask(long id) {
    System.out.printf("Running long task for id: %d...%n", id);
    return "Long task for id " + id + " is done";
  }
  
  public String shortTask(long id){
    System.out.printf("Running short task for id: %d...%n", id);
    return "Short task for id " + id + " is done";
  }

}
