package com.javacreed.examples.spring.hiwj;

import org.springframework.dao.NonTransientDataAccessException;

public class InterruptedOperationException extends NonTransientDataAccessException {

  private static final long serialVersionUID = 1889326906879607551L;
  
  private static final String DEFAULT_MESSAGE = "Interrupted while performing database operation";

  public InterruptedOperationException() {
    super(InterruptedOperationException.DEFAULT_MESSAGE);
  }

  public InterruptedOperationException(final InterruptedException e) {
    super(InterruptedOperationException.DEFAULT_MESSAGE, e);
  }

  public InterruptedOperationException(final String message) {
    super(message);
  }

  public InterruptedOperationException(final String message, final InterruptedException e) {
    super(message, e);
  }

}
