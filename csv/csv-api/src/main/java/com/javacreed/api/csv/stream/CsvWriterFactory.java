package com.javacreed.api.csv.stream;

public interface CsvWriterFactory {

  CsvWriter create(Appendable appendable);

  void setValueSeparator(String valueSeparator);
}
