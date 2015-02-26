/*
 * #%L
 * Change the Video Speed with VLC
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
package com.javacreed.examples.vlc;

public enum VideoSpeed {

  VERY_SLOW("Very Slow", 0.1f), SLOW("Slow", 0.5f), NORMAL("Normal", 1), FAST("Fast", 2), VERY_FAST("Very Fast", 10);

  private final String title;
  private final float rate;

  private VideoSpeed(final String title, final float rate) {
    this.title = title;
    this.rate = rate;
  }

  public float getRate() {
    return rate;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public String toString() {
    return title;
  }

}
