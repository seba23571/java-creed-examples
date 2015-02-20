/*
 * #%L
 * How To Stream ResultSet using JdbcTemplate
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 - 2015 Java Creed
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.javacreed.examples.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CreateBigTable {

  public static final Logger LOGGER = LoggerFactory.getLogger(CreateBigTable.class);

  public static void main(final String[] args) throws Exception {

    final ComboPooledDataSource ds = DbUtils.createDs();
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      CreateBigTable.LOGGER.debug("Dropping table (if exists)");
      jdbcTemplate.update("DROP TABLE IF EXISTS `big_table`");

      CreateBigTable.LOGGER.debug("Creating table");
      jdbcTemplate.update("CREATE TABLE `big_table`(`id` INT(20), `name` VARCHAR(128))");

      CreateBigTable.LOGGER.debug("Adding records");
      final StringBuilder query = new StringBuilder();
      for (int i = 0, b = 1; i < 1000000; i++) {
        if (i % 500 == 0) {
          if (i > 0) {
            CreateBigTable.LOGGER.debug("Adding records (batch {})", b++);
            jdbcTemplate.update(query.toString());
            query.setLength(0);
          }

          query.append("INSERT INTO `big_table` (`id`, `name`) VALUES ");
        } else {
          query.append(",");
        }

        query.append("  (" + (i + 1) + ", 'Pack my box with five dozen liquor jugs.')");
      }

      CreateBigTable.LOGGER.debug("Adding last batch");
      jdbcTemplate.update(query.toString());
    } finally {
      DbUtils.closeQuietly(ds);
    }

    CreateBigTable.LOGGER.debug("Done");
  }
}
