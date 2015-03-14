package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.ReadEvent;

public class OrderStatusEvent extends ReadEvent {
  public static OrderStatusEvent notFound(final UUID key) {
    final OrderStatusEvent ev = new OrderStatusEvent(key);
    ev.entityFound = false;
    return ev;
  }

  private final UUID key;

  private OrderStatusDetails orderStatus;

  private OrderStatusEvent(final UUID key) {
    this.key = key;
  }

  public OrderStatusEvent(final UUID key, final OrderStatusDetails orderStatus) {
    this.key = key;
    this.orderStatus = orderStatus;
  }

  public UUID getKey() {
    return key;
  }

  public OrderStatusDetails getOrderStatus() {
    return orderStatus;
  }
}
