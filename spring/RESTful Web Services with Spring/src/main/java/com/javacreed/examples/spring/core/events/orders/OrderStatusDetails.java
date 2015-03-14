package com.javacreed.examples.spring.core.events.orders;

import java.util.Date;

public class OrderStatusDetails {

  private final Date statusDate;
  private final String status;

  public OrderStatusDetails(final Date statusDate, final String status) {
    this.status = status;
    this.statusDate = statusDate;
  }

  public String getStatus() {
    return status;
  }

  public Date getStatusDate() {
    return statusDate;
  }
}
