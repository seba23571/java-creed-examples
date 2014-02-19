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
package com.javacreed.examples.collections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Assert;
import org.junit.Test;

public class CollectionsUtilTest {

  @Test
  public void test() {
    final List<String> list = new CopyOnWriteArrayList<>();

    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "3"));
    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "2"));
    Assert.assertEquals(0, CollectionsUtil.addInOrder(list, "1"));

    Assert.assertEquals(3, CollectionsUtil.addInOrder(list, "3"));

    Assert.assertEquals(4, list.size());
    Assert.assertEquals("1", list.get(0));
    Assert.assertEquals("2", list.get(1));
    Assert.assertEquals("3", list.get(2));
    Assert.assertEquals("3", list.get(3));
  }

}
