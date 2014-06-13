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
package com.javacreed.examples.collections.list;

public class TestListPerformance {

  public static void run() {

    final ListFactory[] factories = {//@formatter:off 
        ListFactory.VECTOR_FACTORY,
        ListFactory.VECTOR_WITH_SIZE_FACTORY,
				ListFactory.ARRAY_LIST_FACTORY,
				ListFactory.ARRAY_LIST_WITH_SIZE_FACTORY,
				ListFactory.LINKED_LIST_FACTORY, 
				ListFactory.STACK_FACTORY,
				ListFactory.COWAL_FACTORY };//@formatter:on

    final TimeListAction[] listActions = {//@formatter:off  
        new TimeListAddAction(), 
        new TimeListSetAction(), 
        new TimeListGetAction(),
        new TimeListIteratorAction(), 
        new TimeListSizeAction() };//@formatter:on

    System.out.printf("  %-22s", "List Type");
    for (final TimeListAction listAction : listActions) {
      System.out.printf("|  %-13s", listAction.getName());
    }
    System.out.println();

    for (final ListFactory factory : factories) {
      TestListPerformance.run(10000, 100, factory, listActions);
    }
  }

  private static void run(final int limit, final int runs, final ListFactory listFactory,
      final TimeListAction... timeListActions) {

    System.out.printf("%-24s", listFactory.getName());

    for (final TimeListAction timeListAction : timeListActions) {
      long timeInNs = 0;

      System.gc();
      for (int i = 0; i < runs; i++) {
        final long time = timeListAction.timeAction(listFactory.create(limit), limit);
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
