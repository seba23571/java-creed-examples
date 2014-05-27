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
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.io.RafHelper.Mode;

public class RafHelperTest {

  private static File createSampleFile(final String data) throws IOException {
    final File file = File.createTempFile("RafHelper", "test");
    file.deleteOnExit();
    FileUtils.write(file, data, "UTF-8");

    return file;
  }

  @Test
  public void test() throws Exception {
    final String data = FileUtils.readFileToString(new File(getClass().getResource("/samples/Cologne.txt").toURI()),
        "UTF-8");

    final File file = RafHelperTest.createSampleFile(data);

    try (RafHelper helper = new RafHelper(file, Mode.READ_WRITE)) {
      helper.lock();
      Assert.assertEquals(data, helper.read("UTF-8"));
      helper.clear();
      Assert.assertEquals("", helper.read("UTF-8"));
    }
  }
}
