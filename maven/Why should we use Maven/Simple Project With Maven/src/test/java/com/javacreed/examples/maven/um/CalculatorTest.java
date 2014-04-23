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
package com.javacreed.examples.maven.um;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
  @Test
  public void testAdd() {
    final Calculator calc = new Calculator();
    Assert.assertEquals(20, calc.add(12, 5, 3), 0.001);
  }

  @Test
  public void testParse() {
    final Calculator calc = new Calculator();
    final double[] numbers = calc.parseNumbers("12 5 3");
    Assert.assertArrayEquals(new double[] { 12, 5, 3 }, numbers, 0.001);
  }
}
