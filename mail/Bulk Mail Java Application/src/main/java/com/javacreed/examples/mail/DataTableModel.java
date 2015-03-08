/*
 * #%L
 * Bulk Mail Java Application
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
package com.javacreed.examples.mail;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Albert on 06/02/2015.
 */
public class DataTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 3436341791086935780L;

  private String[] headers = new String[0];
  private String[][] values = new String[0][0];

  public String getCellValueAt(final int rowIndex, final String columnName) {
    final int columnIndex = getColumnIndex(columnName);
    if (columnIndex == -1) {
      throw new IllegalArgumentException("Column with name '" + columnName + "' was not found");
    }

    return getValueAt(rowIndex, columnIndex);
  }

  @Override
  public int getColumnCount() {
    return headers.length;
  }

  public int getColumnIndex(final String columnName) {
    for (int columnIndex = 0; columnIndex < headers.length; columnIndex++) {

      if (headers[columnIndex].equals(columnName)) {
        return columnIndex;
      }
    }

    return -1;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return headers[columnIndex];
  }

  @Override
  public int getRowCount() {
    return values.length;
  }

  @Override
  public String getValueAt(final int rowIndex, final int columnIndex) {
    return values[rowIndex][columnIndex];
  }

  public void setData(final String[] headers, final String[][] values) {
    this.headers = Arrays.copyOf(headers, headers.length);
    this.values = new String[values.length][];
    for (int i = 0; i < values.length; i++) {
      this.values[i] = Arrays.copyOf(values[i], headers.length);
    }

    fireTableStructureChanged();
  }
}
