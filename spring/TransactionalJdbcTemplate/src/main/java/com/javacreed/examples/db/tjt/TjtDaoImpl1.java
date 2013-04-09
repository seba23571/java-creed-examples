package com.javacreed.examples.db.tjt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
public class TjtDaoImpl1 implements TjtDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional()
  public void save(final String value) {
    jdbcTemplate.update("INSERT INTO T1 VALUES (?)", value);
    jdbcTemplate.update("INSERT INTO T2 VALUES (?)", value);
  }
}
