/**
 * Copyright 2012-2014 Java Creed.
 * 
 * Licensed under the Apache License, Version 2.0 (the "<em>License</em>");
 * you may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at: 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package com.javacreed.examples.lang;

import java.lang.reflect.Method;

import org.junit.Test;

import com.javacreed.examples.lang.DynamicClassLoader;

public class DynamicClassLoaderTest {

  @Test
  public void test() throws Exception {
    DynamicClassLoader dcl = new DynamicClassLoader(getClass().getClassLoader());
    dcl.loadJar(getClass().getResourceAsStream("/samples/collections-performance-0.0.1-SNAPSHOT.jar"));
    Class<?> main = dcl.loadClass("com.javacreed.examples.collections.Main");
    Method mainMethod = main.getMethod("main", String[].class);
    mainMethod.invoke(null, new Object[] { new String[] {} });
  }
}
