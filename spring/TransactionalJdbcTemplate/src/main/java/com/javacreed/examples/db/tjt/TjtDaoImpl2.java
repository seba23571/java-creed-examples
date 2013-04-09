package com.javacreed.examples.db.tjt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
public class TjtDaoImpl2 implements TjtDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private void insert(final String tableName, final String value) {
    jdbcTemplate.update("INSERT INTO " + tableName + " VALUES (?)", value);
  }

  @Override
  @Transactional("tjtJTransactionManager")
  public void save(final String value) {
    insert("T1", value);
    insert("T2", value);
  }
}
