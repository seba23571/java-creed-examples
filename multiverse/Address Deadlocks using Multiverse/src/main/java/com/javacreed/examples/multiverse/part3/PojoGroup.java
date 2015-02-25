/*
 * #%L
 * Address Deadlocks using Multiverse
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
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
package com.javacreed.examples.multiverse.part3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.GuardedBy;

public class PojoGroup {

  @GuardedBy("this")
  private final List<PojoMember> members = new ArrayList<>();

  public synchronized void add(final PojoMember member) {
    members.add(member);
    member.setGroup(this);
  }

  public synchronized double calculateAverage() {
    // This method call is added to simulate the deadlock
    waitABit();

    if (members.isEmpty()) {
      return 0;
    }

    double total = 0;

    for (final PojoMember member : members) {
      total += member.getScore();
    }

    return total / members.size();
  }

  /**
   * This method is added to simulate the deadlock
   */
  protected void waitABit() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
