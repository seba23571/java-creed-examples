package com.javacreed.examples.spring.rest.domain;

import java.util.Date;
import java.util.UUID;

import com.javacreed.examples.spring.core.events.orders.OrderStatusDetails;

public class OrderStatus {

  public static OrderStatus fromOrderStatusDetails(final UUID key, final OrderStatusDetails orderDetails) {
    final OrderStatus status = new OrderStatus();

    status.orderId = key;
    status.status = orderDetails.getStatus();
    status.statusDate = orderDetails.getStatusDate();

    return status;
  }

  private UUID orderId;

  private Date statusDate;

  private String status;

  public UUID getOrderId() {
    return orderId;
  }

  public String getStatus() {
    return status;
  }

  public Date getStatusDate() {
    return statusDate;
  }
}
