package com.javacreed.examples.flyway;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class Example2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Example2.class);

  public static void main(final String[] args) {
    Example2.LOGGER.debug("Loading the spring context");
    try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "/META-INF/application-context.xml")) {
      final JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

      Example2.LOGGER.debug("Name      | Surname");
      Example2.LOGGER.debug("-----------------------");
      jdbcTemplate.query("SELECT * FROM `sample_table`", new RowCallbackHandler() {
        @Override
        public void processRow(final ResultSet resultSet) throws SQLException {
          Example2.LOGGER.debug(String.format("%-10s| %-10s", resultSet.getString("name"),
              resultSet.getString("surname")));
        }
      });
      Example2.LOGGER.debug("-----------------------");
    }
  }
}
