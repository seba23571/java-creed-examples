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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Albert on 06/02/2015.
 */
public class VariablesTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -4650828037160732957L;

  private static final String[] COLUMN_NAMES = { "Variable Name", "Data Column" };

  private final List<VariableColumnBinding> values = new ArrayList<>();

  @Override
  public int getColumnCount() {
    return VariablesTableModel.COLUMN_NAMES.length;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return VariablesTableModel.COLUMN_NAMES[columnIndex];
  }

  @Override
  public int getRowCount() {
    return values.size();
  }

  public VariableColumnBinding getRowValueAt(final int rowIndex) {
    return values.get(rowIndex);
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    final VariableColumnBinding row = getRowValueAt(rowIndex);
    switch (columnIndex) {
    case 0:
      return row.getVariableName();
    case 1:
      return row.getDataColumn();
    }
    return null;
  }

  public List<VariableColumnBinding> getValues() {
    return values;
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return columnIndex == 1;
  }

  @Override
  public void setValueAt(final Object value, final int rowIndex, final int columnIndex) {
    final VariableColumnBinding row = getRowValueAt(rowIndex);
    switch (columnIndex) {
    case 1:
      row.setDataColumn((String) value);
      break;
    }

  }

  public void setVariables(final Set<String> variables) {
    values.clear();
    values.add(new VariableColumnBinding("Email"));
    for (final String variable : variables) {
      values.add(new VariableColumnBinding(variable));
    }
      fireTableDataChanged();
  }
}