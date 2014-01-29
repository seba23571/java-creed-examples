package com.javacreed.examples.flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;

public class Example1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example1.class);

  public static void main(final String[] args) throws Exception {
    final Flyway flyway = new Flyway();
    flyway.setDataSource("jdbc:h2:file:target/data", "sa", null);
    flyway.migrate();

    try (Connection conn = DriverManager.getConnection("jdbc:h2:file:target/data", "sa", null);
        Statement stmt = conn.createStatement()) {
      try (ResultSet resultSet = stmt.executeQuery("SELECT * FROM `sample_table`")) {
        Example1.LOGGER.debug("Name      | Surname");
        Example1.LOGGER.debug("-----------------------");
        while (resultSet.next()) {
          Example1.LOGGER.debug(String.format("%-10s| %-10s", resultSet.getString("name"),
              resultSet.getString("surname")));
        }
        Example1.LOGGER.debug("-----------------------");
      }
    }
  }

}
