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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
    DbHelper.getInstance().init();

    try {
      final Contact c = new Contact();
      c.setName("Albert Attard");
      c.setContacts("albert@javacreed.com");
      c.save();

      try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
        Main.LOGGER.debug("Contacts");
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
          while (rs.next()) {
            Main.LOGGER.debug("  >> [{}] {} ({})", rs.getInt("id"), rs.getString("name"), rs.getString("contacts"));
          }
        }
      }

    } catch (final SQLException e) {
      Main.LOGGER.error("Failed to save the contact", e);
    }

    DbHelper.getInstance().close();
    Main.LOGGER.info("Done");
  }
}
