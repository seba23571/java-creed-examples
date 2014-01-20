package com.javacreed.examples.flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.googlecode.flyway.core.Flyway;

public class Example1 {

    public static void main(final String[] args) throws Exception {
        final Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:file:target/data", "sa", null);
        flyway.migrate();

        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:target/data", "sa", null); Statement stmt = conn.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT * FROM `sample_table`")) {
                System.out.println("Name      | Surname");
                System.out.println("-----------------------");
                while (resultSet.next()) {
                    System.out.printf("%-10s|", resultSet.getString("name"));
                    System.out.printf(" %-10s%n", resultSet.getString("surname"));
                }
                System.out.println("-----------------------");
            }
        }
    }

}
