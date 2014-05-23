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
import java.util.Collection;

public class Employee {

    public static Collection<Employee> createSample() {
        final Collection<Employee> collection = new ArrayList<>();
        collection.add(new Employee(1, "Albert Attard", "Java Programming"));
        collection.add(new Employee(2, "Joe Borg", "Java Programming"));
        collection.add(new Employee(3, "Mary Vella", "Java Programming"));
        collection.add(new Employee(4, "Peter White", "Databases"));
        collection.add(new Employee(5, "John Smith", "Databases"));
        return collection;
    }

    private int id;
    private String name;
    private String departments;

    public Employee() {}

    public Employee(final int id, final String name, final String departments) {
        this.id = id;
        this.name = name;
        this.departments = departments;
    }

    public String getDepartments() {
        return departments;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDepartments(final String departments) {
        this.departments = departments;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
