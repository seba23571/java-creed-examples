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
package com.javacreed.examples.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ExampleDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public long addNew(final String name) {
    final PreparedStatementCreator psc = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
        final PreparedStatement ps = connection.prepareStatement("INSERT INTO `names` (`name`) VALUES (?)",
            Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        return ps;
      }
    };

    // The newly generated key will be saved in this object
    final KeyHolder holder = new GeneratedKeyHolder();

    jdbcTemplate.update(psc, holder);

    final long newNameId = holder.getKey().longValue();
    return newNameId;
  }
}
