package com.javacreed.examples.collections.map;


public class TestMapPerformance {

  public static void run() {

    final MapFactory[] factories = {//@formatter:off 
        MapFactory.HASHTABLE_FACTORY,
        MapFactory.HASHTABLE_WITH_SIZE_FACTORY,
        MapFactory.TREE_MAP_FACTORY,
				MapFactory.HASH_MAP_FACTORY,
				MapFactory.HASH_MAP_WITH_SIZE_FACTORY };//@formatter:on

    final TimeMapAction[] mapActions = {//@formatter:off  
        new TimeMapPutAction(), 
        new TimeMapGetAction(),
        new TimeMapSizeAction() };//@formatter:on

    System.out.printf("  %-22s", "Map Type");
    for (final TimeMapAction mapAction : mapActions) {
      System.out.printf("|  %-13s", mapAction.getName());
    }
    System.out.println();

    for (final MapFactory factory : factories) {
      TestMapPerformance.run(100000, 100, factory, mapActions);
    }
  }

  private static void run(final int limit, final int runs, final MapFactory listFactory,
      final TimeMapAction... timeMapActions) {

    System.out.printf("%-24s", listFactory.getName());

    for (final TimeMapAction timeMapAction : timeMapActions) {
      long timeInNs = 0;

      System.gc();
      for (int i = 0; i < runs; i++) {
        final long time = timeMapAction.timeAction(listFactory.create(limit), limit);
        timeInNs += time;
      }

      final long avgTimeInNs = timeInNs / runs;
      final double avgTimeInMs = avgTimeInNs / 1000000D;
      System.out.printf("| %8.3f Mills", avgTimeInMs);
    }

    System.out.println();
    System.gc();
  }
}
