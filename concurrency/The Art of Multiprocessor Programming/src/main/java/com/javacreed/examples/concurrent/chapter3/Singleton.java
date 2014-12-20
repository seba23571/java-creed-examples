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

/**
 * Need to read more about this as I am not sure I understand why this class is broken.
 */
@Figure("3.10")
@Broken("The constructor call appears to take place before the instance field is assigned, but the Java memory model allows these steps to occur out of order, effectively making a partially initialized Singleton object visible to other programs")
public class Singleton {

  public static Singleton getInstance() {
    if (Singleton.INSTANCE == null) {
      synchronized (Singleton.class) {
        if (Singleton.INSTANCE == null) {
          Singleton.INSTANCE = new Singleton();
        }
      }
    }

    return Singleton.INSTANCE;
  }

  private static Singleton INSTANCE;

  private Singleton() {}

}
