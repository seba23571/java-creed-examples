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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactsHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactsHelper.class);

  private static final ContactsHelper INSTANCE = new ContactsHelper();

  public static ContactsHelper getInstance() {
    return ContactsHelper.INSTANCE;
  }

  public List<Contact> getContacts() throws SQLException {
    ContactsHelper.LOGGER.debug("Loading contacts");
    final List<Contact> contacts = new ArrayList<>();

    final String sql = "SELECT * FROM contacts ORDER BY id";
    try (Connection connection = DbHelper.getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery()) {

      while (rs.next()) {
        final Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setName(rs.getString("name"));
        contact.setContacts(rs.getString("contacts"));
        contacts.add(contact);
      }
    }

    ContactsHelper.LOGGER.debug("Loaded {} contacts", contacts.size());
    return contacts;
  }
}
