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
import java.sql.SQLException;

public class Contact {

  private long id;
  private String name;
  private String contacts;

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
    final String sql = "INSERT INTO contacts(name, contacts) VALUES(?, ?)";
    try (Connection connection = DbHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, name);
      pstmt.setString(2, contacts);
      pstmt.execute();
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

}
