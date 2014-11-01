package com.javacreed.api.csv;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.javacreed.api.common.Person;
import com.javacreed.api.csv.Csv.Builder;

public class CsvToCsvTest {

  @Test
  public void test() {
    final Builder builder = new Builder();
    final Csv csv = builder.build();

    final List<Person> persons = new ArrayList<>();
    persons.add(new Person("Albert", "Attard", 18));

    final StringWriter out = new StringWriter();
    csv.toCsv(out, persons, Person.class);

    System.out.println(out);
  }

}
