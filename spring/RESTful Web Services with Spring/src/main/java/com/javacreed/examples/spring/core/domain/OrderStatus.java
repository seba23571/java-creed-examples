package com.javacreed.examples.spring.core.domain;

import java.util.Date;

import com.javacreed.examples.spring.core.events.orders.OrderStatusDetails;

public class OrderStatus {

  private final Date statusDate;
  private final String status;

  public OrderStatus(final Date date, final String status) {
    this.status = status;
    this.statusDate = date;
  }

  public String getStatus() {
    return status;
  }

  public Date getStatusDate() {
    return statusDate;
  }

  public OrderStatusDetails toStatusDetails() {
    return new OrderStatusDetails(statusDate, status);
  }
}
