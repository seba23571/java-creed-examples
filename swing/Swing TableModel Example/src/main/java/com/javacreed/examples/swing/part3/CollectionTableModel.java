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
package com.javacreed.examples.swing.part3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CollectionTableModel<T> extends AbstractTableModel {

    private static final long serialVersionUID = 6287616108297893903L;

    protected final List<T> rows = new ArrayList<>();

    private final List<ColumnHelper<T>> columns;

    public CollectionTableModel(final Collection<ColumnHelper<T>> columns) {
        this.columns = new ArrayList<>(columns);
    }

    @SafeVarargs
    public CollectionTableModel(final ColumnHelper<T>... columns) {
        this(Arrays.asList(columns));
    }

    public void add(final T entry) {
        final int index = rows.size();
        rows.add(entry);
        fireTableRowsInserted(index, index);
    }

    public void addAll(final Collection<T> entries) {
        final int index = rows.size();
        rows.addAll(entries);
        fireTableRowsInserted(index, rows.size() - 1);
    }

    public void clear() {
        rows.clear();
        fireTableDataChanged();
    }

    protected Object getCellValue(final T object, final int columnIndex) {
        return columns.get(columnIndex).getValue(object);
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columns.get(columnIndex).getType();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    public T getRowValue(final int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final T object = getRowValue(rowIndex);
        return getCellValue(object, columnIndex);
    }

    public void set(final Collection<T> entries) {
        this.rows.clear();
        this.rows.addAll(entries);
        fireTableDataChanged();
    }

}
