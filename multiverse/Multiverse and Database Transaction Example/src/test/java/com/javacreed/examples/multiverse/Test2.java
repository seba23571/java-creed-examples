/*
 * #%L
 * Multiverse and Database Transaction Example
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
package com.javacreed.examples.multiverse;

import org.junit.Assert;
import org.junit.Test;

public class Test2 extends AbstractTest {

  @Test
  public void test() {
    final Account a = load(1);
    Assert.assertEquals(10, a.getBalance());

    final Account b = load(2);
    Assert.assertEquals(5, b.getBalance());

    try {
      teller.transfer(b, a, 10);
      Assert.fail("Should throw InsufficientFundsException");
    } catch (final InsufficientFundsException e) {// Expected
    }

    Assert.assertEquals(10, a.getBalance());
    Assert.assertEquals(10, getBalance(1));

    Assert.assertEquals(5, b.getBalance());
    Assert.assertEquals(5, getBalance(2));
  }

}
