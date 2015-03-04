/*
 * #%L
 * Memory Overhead when using Multiverse
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

import org.slf4j.Logger;

import com.javacreed.examples.multiverse.utils.LoggerUtils;

public class Example5 {

  public static final Logger LOGGER = LoggerUtils.getLogger(Example5.class);

  public static void main(final String[] args) throws InterruptedException {

    final int limit = 10000000;
    final List<PojoDomainObject> list = new ArrayList<>(limit);
    for (int i = 0; i < limit; i++) {
      list.add(new PojoDomainObject(i));
    }

    Example5.LOGGER.debug("Done.");
    TimeUnit.SECONDS.sleep(5);
  }

}
