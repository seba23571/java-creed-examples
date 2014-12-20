/*
 * #%L
 * The Art of Multiprocessor Programming
 * $Id:$
 * $HeadURL:$
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
package com.javacreed.examples.concurrent.chapter3;

import org.junit.Assert;
import org.junit.Test;

public class LockBasedQueueTest {

  @Test
  public void test() {
    final LockBasedQueue<String> queue = new LockBasedQueue<String>(3);
    queue.enq("a");
    queue.enq("b");
    queue.enq("c");

    Assert.assertEquals("a", queue.deq());
    Assert.assertEquals("b", queue.deq());
    Assert.assertEquals("c", queue.deq());

    queue.enq("d");
    Assert.assertEquals("d", queue.deq());
  }

}
