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

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Application extends JFrame {

    private static final long serialVersionUID = 2183549477252245943L;

    public Application() {
        setLayout(new GridLayout(2, 1));

        final CollectionTableModel<Employee> employeeTableModel = new CollectionTableModel<>(//@formatter:off
                new ColumnHelper<Employee>("ID", int.class) { @Override public Object getValue(final Employee t) { return t.getId(); }},
                new ColumnHelper<Employee>("Name", String.class) { @Override public Object getValue(final Employee t) { return t.getName(); }},
                new ColumnHelper<Employee>("Description", String.class) { @Override public Object getValue(final Employee t) { return t.getDepartments(); }}
            );//@formatter:on
        employeeTableModel.set(Employee.createSample());
        add(new JScrollPane(new JTable(employeeTableModel)));

        final CollectionTableModel<Item> itemTableModel = new CollectionTableModel<>(//@formatter:off
                new ColumnHelper<Item>("ID", int.class) { @Override public Object getValue(final Item t) { return t.getId(); }},
                new ColumnHelper<Item>("Name", String.class) { @Override public Object getValue(final Item t) { return t.getName(); }}
            );//@formatter:on
        itemTableModel.set(Item.createSample());
        add(new JScrollPane(new JTable(itemTableModel)));
    }

}
