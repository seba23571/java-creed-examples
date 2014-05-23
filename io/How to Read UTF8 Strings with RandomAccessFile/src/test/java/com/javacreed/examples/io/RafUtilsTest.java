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
package com.javacreed.examples.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class RafUtilsTest {

  @Test
  public void test() throws Exception {
    // Make sure that the buffer size does not affect the outcome of this method
    for (int bufferSize = 1; bufferSize < 1025; bufferSize *= 2) {
      // Make sure that the UTF-8 Characters are properly read
      File file = new File(getClass().getResource("/samples/Cologne.txt").toURI());
      String data = RafUtils.lockAndReadFile(file, "UTF-8", bufferSize);
      Assert.assertEquals(425, data.length());
      Assert.assertTrue(data.contains("Köln [kœln], Colognian: Kölle [ˈkœɫə])"));

      // Make sure that the ASCII Characters are properly read
      file = new File(getClass().getResource("/samples/Milan.txt").toURI());
      data = RafUtils.lockAndReadFile(file, "ASCII", bufferSize);
      Assert.assertEquals(296, data.length());
    }
  }

}
