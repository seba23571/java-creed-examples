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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Example1 {

  private static class Data {
    public static final RowMapper<Data> ROW_MAPPER = new RowMapper<Data>() {
      @Override
      public Data mapRow(final ResultSet rs, final int i) throws SQLException {
        return new Data(rs.getLong("id"), rs.getString("name"));
      }
    };
    private final long id;
    private final String name;

    public Data(final long id, final String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public String toString() {
      return String.format("[%d] %s", id, name);
    }
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(Example1.class);

  public static void main(final String[] args) throws Exception {
    final ComboPooledDataSource ds = DbUtils.createDs();
    try {
      final JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

      Example1.LOGGER.debug("Reading all rows");
      final List<Data> rows = jdbcTemplate.query("SELECT * FROM `big_table`", Data.ROW_MAPPER);

      Example1.LOGGER.debug("All records read ({} records)", rows.size());

      // Sleep a bit so that it shows better on the profiler
      TimeUnit.SECONDS.sleep(10);
    } finally {
      DbUtils.closeQuietly(ds);
    }
    Example1.LOGGER.debug("Done");
  }
}
