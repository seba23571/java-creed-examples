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
package com.javacreed.examples.swing.part2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = -125507371735960002L;

    private static final String[] COLUMNS = { "ID", "Name" };

    private final List<Item> rows = new ArrayList<>();

    @Override
    public int getColumnCount() {
        return ItemTableModel.COLUMNS.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return ItemTableModel.COLUMNS[columnIndex];
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Item item = rows.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return item.getId();
        case 1:
            return item.getName();
        }
        return null;
    }

    public void setItems(final Collection<Item> items) {
        rows.clear();
        rows.addAll(items);
        fireTableDataChanged();
    }

}
