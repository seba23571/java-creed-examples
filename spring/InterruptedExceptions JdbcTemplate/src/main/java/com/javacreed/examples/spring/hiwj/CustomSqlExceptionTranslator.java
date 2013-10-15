package com.javacreed.examples.spring.hiwj;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodes;

public class CustomSqlExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

  public CustomSqlExceptionTranslator() {
    super();
  }

  public CustomSqlExceptionTranslator(final DataSource dataSource) {
    super(dataSource);
  }

  public CustomSqlExceptionTranslator(final SQLErrorCodes errorCodes) {
    super(errorCodes);
  }

  public CustomSqlExceptionTranslator(final String dbName) {
    super(dbName);
  }

  @Override
  protected DataAccessException doTranslate(final String task, final String sql, final SQLException e) {
    for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
      if (cause instanceof InterruptedException) {
        Thread.currentThread().interrupt();
        return new InterruptedOperationException((InterruptedException) cause);
      }
    }

    return super.doTranslate(task, sql, e);
  }
}
