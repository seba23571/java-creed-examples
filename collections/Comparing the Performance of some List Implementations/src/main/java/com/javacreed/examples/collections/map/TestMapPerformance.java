/*
 * #%L
 * Comparing the Performance of some List Implementations
 * $Id:$
 * $HeadURL$
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
