package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.ReadEvent;

public class OrderDetailsEvent extends ReadEvent {
  public static OrderDetailsEvent notFound(final UUID key) {
    final OrderDetailsEvent ev = new OrderDetailsEvent(key);
    ev.entityFound = false;
    return ev;
  }

  private final UUID key;

  private OrderDetails orderDetails;

  private OrderDetailsEvent(final UUID key) {
    this.key = key;
  }

  public OrderDetailsEvent(final UUID key, final OrderDetails orderDetails) {
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
