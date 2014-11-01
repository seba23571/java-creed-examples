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
package com.javacreed.api.csv;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.common.Person;

public class CsvTest {

  @Test
  public void test() {
    final Csv csv = new Csv();

    final List<Person> persons = new ArrayList<>();
    persons.add(new Person("Albert", "Attard", 18));
    persons.add(new Person("Mary", "Borg", 19));
    persons.add(new Person("Joe", "Borg", 20));

    final StringWriter out = new StringWriter();
    csv.toCsv(out, persons, Person.class);

    final List<Person> actual = csv.fromCsv(out.toString(), Person.class);
    Assert.assertNotNull(actual);
    Assert.assertEquals(persons, actual);
  }

}
