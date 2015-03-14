package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.UpdatedEvent;

public class OrderUpdatedEvent extends UpdatedEvent {

  private final UUID key;
  private final OrderDetails orderDetails;

  public OrderUpdatedEvent(final UUID key, final OrderDetails orderDetails) {
    this.key = key;
    this.orderDetails = orderDetails;
  }

  public UUID getKey() {
    return key;
  }

  public OrderDetails getOrderDetails() {
    return orderDetails;
  }
}
