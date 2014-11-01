/*
 * #%L
 * JavaCreed CSV API
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
package com.javacreed.api.csv.adapter;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.common.Person;
import com.javacreed.api.csv.Csv;

public class CustomValueSeparatorTest {

  @Test
  public void test() {
    final Csv.Builder builder = new Csv.Builder();
    builder.setValueSeparator("|");
    final Csv csv = builder.build();

    final Reader input = new StringReader(//@formatter:off
        "name|surname|age\r\n" + 
        "Albert|Attard|22\r\n" + 
        "Aarón|Lines|24\r\n" + 
        "Tom|Genç|19\r\n" + 
        "Hans-Jürgen|Güvencer|32\r\n");//@formatter:on

    final Object[][] expected = {//@formatter:off
        {"Albert", "Attard", 22},
        {"Aarón", "Lines", 24},
        {"Tom", "Genç", 19},
        {"Hans-Jürgen", "Güvencer", 32},
    };//@formatter:on

    final List<Person> list = csv.fromCsv(input, Person.class);
    Assert.assertNotNull(list);
    Assert.assertEquals(expected.length, list.size());

    for (int i = 0; i < expected.length; i++) {
      final Person person = list.get(i);
      Assert.assertNotNull(person);
      Assert.assertEquals(expected[i][0], person.getName());
      Assert.assertEquals(expected[i][1], person.getSurname());
      Assert.assertEquals(expected[i][2], person.getAge());
    }

  }

}
