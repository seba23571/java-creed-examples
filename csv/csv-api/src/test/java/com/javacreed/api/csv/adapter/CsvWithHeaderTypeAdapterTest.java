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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.api.common.Person;
import com.javacreed.api.csv.Csv;
import com.javacreed.api.csv.CsvException;

public class CsvWithHeaderTypeAdapterTest {

  private static class PersonCsvTypeAdapterByIndex extends AbstractCsvWithHeaderTypeAdapter<Person> {
    @Override
    protected Person processLine(final RowData rowData) throws CsvException, IOException {
      final Person person = new Person();
      person.setSurname(rowData.getValue(1));
      person.setAge(Integer.parseInt(rowData.getValue(2)));
      person.setName(rowData.getValue(0));
      return person;
    }
  }

  private static class PersonCsvTypeAdapterByName extends AbstractCsvWithHeaderTypeAdapter<Person> {
    @Override
    protected Person processLine(final RowData rowData) throws CsvException, IOException {
      final Person person = new Person();
      person.setAge(Integer.parseInt(rowData.getValue("Age")));
      person.setName(rowData.getValue("Name"));
      person.setSurname(rowData.getValue("Surname"));
      return person;
    }
  }

  private static void testAdapter(final AbstractCsvWithHeaderTypeAdapter<Person> typeAdapter) {
    final Csv.Builder builder = new Csv.Builder();
    builder.registerTypeAdapter(Person.class, typeAdapter);
    final Csv csv = builder.build();

    final Reader input = new StringReader(//@formatter:off
        "Name,Surname,Age\r\n" + 
        "Albert,Attard,22\r\n" + 
        "Aarón,Lines,24\r\n" + 
        "Tom,Genç,19\r\n" + 
        "Hans-Jürgen,Güvencer,32\r\n");//@formatter:on

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

  @Test
  public void test() {
    CsvWithHeaderTypeAdapterTest.testAdapter(new PersonCsvTypeAdapterByName());
    CsvWithHeaderTypeAdapterTest.testAdapter(new PersonCsvTypeAdapterByIndex());
  }
}
