package com.javacreed.api.csv.swing;

import java.util.Objects;

import javax.swing.table.TableModel;

import com.javacreed.api.csv.adapter.AbstractCsvTypeAdapter;

public class TableModelTypeAdapter<T> extends AbstractCsvTypeAdapter<T> {

  private final TableModel tableModel;

  public TableModelTypeAdapter(final TableModel tableModel) {
    this.tableModel = Objects.requireNonNull(tableModel);
  }

}
