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

import org.multiverse.api.StmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class Teller {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional("tjtJTransactionManager")
  public void transfer(final Account from, final Account to, final int amount) throws InsufficientFundsException {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        // Update the objects in memory
        from.adjustBy(-amount);
        to.adjustBy(amount);

        // Update the database
        jdbcTemplate.update("UPDATE `accounts` SET `balance` = ? WHERE `account_id` = ?",
            new Object[] { from.getBalance(), from.getId() });
        jdbcTemplate.update("UPDATE `accounts` SET `balance` = ? WHERE `account_id` = ?",
            new Object[] { to.getBalance(), to.getId() });

        // Validate after the db update on purpose (so we fail after all updates are made)
        if (from.getBalance() < 0 || to.getBalance() < 0) {
          throw new InsufficientFundsException();
        }
      }
    });
  }
}
