/*
 * #%L
 * How To Stream ResultSet using JdbcTemplate
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
package com.javacreed.examples.spring;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DbUtils {

  public static void closeQuietly(final ComboPooledDataSource ds) {
    if (ds != null) {
      try {
        ds.close();
      } catch (final Exception e) {
        // Ignore
      }
    }
  }

  public static ComboPooledDataSource createDs() throws PropertyVetoException {
    final ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setUser("root");
    ds.setPassword("root");
    ds.setDriverClass("com.mysql.jdbc.Driver");
    ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
    return ds;
  }
}
