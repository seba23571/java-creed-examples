/*
 * #%L
 * Multiverse and Database Transaction Example
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
package com.javacreed.examples.multiverse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javacreed.examples.multiverse.utils.LoggerUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/spring-application.xml" })
public class AbstractTest {

  private static final Logger LOGGER = LoggerUtils.getLogger(AbstractTest.class);

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  protected Teller teller;

  protected int getBalance(final int id) {
    return jdbcTemplate.queryForObject("SELECT `balance` FROM `accounts` WHERE `account_id` = ?", new Object[] { id },
        new SingleColumnRowMapper<Integer>(Integer.class));
  }

  @Before
  public void init() {
    AbstractTest.LOGGER.trace("Preparing the database");
    jdbcTemplate.update("TRUNCATE TABLE `accounts`");
    jdbcTemplate.update("INSERT INTO `accounts` VALUES (1, 10)");
    jdbcTemplate.update("INSERT INTO `accounts` VALUES (2, 5)");
  }

  protected Account load(final int id) {
    return jdbcTemplate.queryForObject("SELECT * FROM `accounts` WHERE `account_id` = ?", new Object[] { id },
        new RowMapper<Account>() {
          @Override
          public Account mapRow(final ResultSet resultSet, final int i) throws SQLException {
            final Account account = new Account(resultSet.getInt("account_id"), resultSet.getInt("balance"));
            return account;
          }
        });
  }

}
