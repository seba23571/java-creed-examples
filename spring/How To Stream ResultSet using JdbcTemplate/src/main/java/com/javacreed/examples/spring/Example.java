package com.javacreed.examples.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Example {

    private static final Logger LOGGER = LoggerFactory.getLogger(Example.class);

    public static void main(final String[] args) throws Exception {
        final ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setUser("root");
        ds.setPassword("root");
        ds.setMaxPoolSize(2);
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/test");

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        try {
            Example.LOGGER.debug("Create the test table");
            jdbcTemplate.update("CREATE TABLE `big_table`(`id` INT(20), `name` VARCHAR(128))");

            final int limit = 10000;
            Example.LOGGER.debug("Populating the table with {} records", limit);
            for (int i = 1; i <= limit; i++) {
                jdbcTemplate.update("INSERT INTO `big_table` VALUES (?, ?)", new Object[] { i, "Pack my box with five dozen liquor jugs." });
            }

            Example.LOGGER.debug("Reading all rows");
            jdbcTemplate.query(/*new StreamingStatementCreator(*/"SELECT * FROM `big_table`"/*)*/, new RowCallbackHandler() {
                @Override
                public void processRow(final ResultSet rs) throws SQLException {
                    Example.LOGGER.trace("[{}] {}", rs.getLong("id"), rs.getString("name"));
                }
            });

        } finally {
            jdbcTemplate.update("DROP TABLE `big_table`");
            ds.close();
        }
        Example.LOGGER.debug("Done");
    }
}
