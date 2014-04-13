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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactsHelperTest {

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
  public void testLoad() throws SQLException {
    List<Contact> contacts = ContactsHelper.getInstance().getContacts();
    Assert.assertNotNull(contacts);
    Assert.assertTrue(contacts.isEmpty());

    try (Connection connection = DbHelper.getConnection(); Statement stmt = connection.createStatement()) {
      stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Albert Attard', 'albert@javacreed.com')");
      stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Mary White', 'mary@javacreed.com')");
      stmt.execute("INSERT INTO contacts (name, contacts) VALUES ('Joe Borg', 'joe@javacreed.com')");

      contacts = ContactsHelper.getInstance().getContacts();
      Assert.assertNotNull(contacts);
      Assert.assertEquals(3, contacts.size());

      Contact contact = contacts.get(0);
      Assert.assertNotNull(contact);
      Assert.assertEquals(1L, contact.getId());
      Assert.assertEquals("Albert Attard", contact.getName());
      Assert.assertEquals("albert@javacreed.com", contact.getContacts());

      contact = contacts.get(2);
      Assert.assertNotNull(contact);
      Assert.assertEquals(3L, contact.getId());
      Assert.assertEquals("Joe Borg", contact.getName());
      Assert.assertEquals("joe@javacreed.com", contact.getContacts());
    }
  }
}
