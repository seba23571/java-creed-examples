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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contact {

  private static final Logger LOGGER = LoggerFactory.getLogger(Contact.class);

  private long id = -1L;
  private String name;
  private String contacts;

  public void delete() throws SQLException {
    if (id == -1) {
      // Can throw an exception
    } else {
      Contact.LOGGER.debug("Deleting contact: {}", this);
      final String sql = "DELETE FROM contacts WHERE id = ?";
      try (Connection connection = DbHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setLong(1, id);
        pstmt.execute();
        id = -1;
      }
    }
  }

  public String getContacts() {
    return contacts;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void save() throws SQLException {
    try (Connection connection = DbHelper.getConnection()) {
      if (id == -1) {
        Contact.LOGGER.debug("Adding new contact: {}", this);
        final String sql = "INSERT INTO contacts(name, contacts) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
          pstmt.setString(1, name);
          pstmt.setString(2, contacts);
          pstmt.execute();

          try (final ResultSet rs = pstmt.getGeneratedKeys()) {
            rs.next();
            id = rs.getLong(1);
          }
        }
      } else {
        Contact.LOGGER.debug("Updating existing contact: {}", this);
        final String sql = "UPDATE contacts SET name = ?, contacts = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
          pstmt.setString(1, name);
          pstmt.setString(2, contacts);
          pstmt.setLong(3, id);
          pstmt.execute();
        }
      }
    }
  }

  public void setContacts(final String contacts) {
    this.contacts = contacts;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    if (id == -1) {
      formatted.append("[No Id] ");
    } else {
      formatted.append("[").append(id).append("] ");
    }

    if (name == null) {
      formatted.append("no name");
    } else {
      formatted.append(name);
    }

    return formatted.toString();
  }

}
