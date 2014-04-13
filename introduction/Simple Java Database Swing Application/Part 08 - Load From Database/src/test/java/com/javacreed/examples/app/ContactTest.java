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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {

  @After
  public void close() {
    DbHelper.getInstance().close();
  }

  @Before
  public void init() throws SQLException {
    DbHelper.getInstance().init();

    try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
      stmt.execute("TRUNCATE TABLE contacts");
      stmt.execute("ALTER TABLE contacts ALTER COLUMN id RESTART WITH 1");
    }
  }

  @Test
  public void testSave() throws SQLException {
    final Contact c = new Contact();
    c.setName("Albert Attard");
    c.setContacts("albert@javacreed.com");
    c.save();

    try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
      try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM contacts")) {
        Assert.assertTrue("Count should return at least one row", rs.next());
        Assert.assertEquals(1L, rs.getLong(1));
        Assert.assertFalse("Count should not return more than one row", rs.next());
      }

      try (ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
        Assert.assertTrue("Select should return at least one row", rs.next());
        Assert.assertEquals(1L, rs.getLong(1));
        Assert.assertEquals("Albert Attard", rs.getString("name"));
        Assert.assertEquals("albert@javacreed.com", rs.getString("contacts"));
        Assert.assertFalse("Select should not return more than one row", rs.next());
      }
    }
  }
}
