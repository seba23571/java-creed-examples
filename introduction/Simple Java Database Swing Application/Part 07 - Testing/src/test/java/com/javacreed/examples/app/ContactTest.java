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
