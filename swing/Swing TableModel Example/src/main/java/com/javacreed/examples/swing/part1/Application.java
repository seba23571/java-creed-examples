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
package com.javacreed.examples.swing.part1;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Application extends JFrame {

    private static final long serialVersionUID = 2183549477252245943L;

    public Application() {
        setLayout(new GridLayout(1, 1));

        final EmployeeTableModel tableModel = new EmployeeTableModel();
        tableModel.setEmployees(Employee.createSample());

        final JTable table = new JTable(tableModel);
        add(new JScrollPane(table));
    }

}
