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
package com.javacreed.examples.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {

    Main.LOGGER.debug("Creating the data source");
    final BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("org.h2.Driver");
    ds.setUrl("jdbc:h2:target/db");
    ds.setUsername("sa");
    ds.setPassword("");

    try {
      Main.LOGGER.debug("Executing Flyway (database migration)");
      final Flyway flyway = new Flyway();
      flyway.setDataSource(ds);
      flyway.migrate();

      try (Connection connection = ds.getConnection(); Statement stmt = connection.createStatement()) {
        stmt.executeUpdate("INSERT INTO contacts(name, contacts) VALUES ('Albert Attard', 'albert@javacreed.com')");

        Main.LOGGER.debug("Contacts");
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
          while (rs.next()) {
            Main.LOGGER.debug("  >> [{}] {} ({})", rs.getInt("id"), rs.getString("name"), rs.getString("contacts"));
          }
        }
      } catch (final SQLException e) {
        Main.LOGGER.error("Failed", e);
      }
    } finally {
      try {
        ds.close();
      } catch (final SQLException e) {
        Main.LOGGER.error("Failed to close the data source", e);
      }
    }
  }
}
