package com.javacreed.examples.collections;

import com.javacreed.examples.collections.list.TestListPerformance;
import com.javacreed.examples.collections.map.TestMapPerformance;

public class Main {

  public static void main(final String[] args) {

    String type = "map";
    if (args.length == 1) {
      type = args[0];
    }

    switch (type.toLowerCase()) {
    case "map":
      TestMapPerformance.run();
      break;
    case "list":
      TestListPerformance.run();
      break;
    default:
      System.out.println("Wrong argument: [map|list]");
    }

  }
}
