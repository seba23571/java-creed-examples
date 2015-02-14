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

import com.javacreed.examples.concurrent.extra.Broken;
import com.javacreed.examples.concurrent.extra.Figure;

@Figure("3.12")
@Broken("The this reference must not be released from the constructor before the constructor returns. The method onEvent() is not guaranteed to see a correct value of x.")
public class EventListener {

  @SuppressWarnings("unused")
  private final int x;

  public EventListener(final EventSource eventSource) {
    x = 3;
    eventSource.registerLIstener(this);
  }

  public void onEvent(final Event e) {
    // Handle the event
  }

}
