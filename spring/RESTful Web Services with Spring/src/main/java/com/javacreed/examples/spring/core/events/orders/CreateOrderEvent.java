package com.javacreed.examples.spring.core.events.orders;

import com.javacreed.examples.spring.core.events.CreateEvent;

public class CreateOrderEvent extends CreateEvent {
  private final OrderDetails details;

  public CreateOrderEvent(final OrderDetails details) {
    this.details = details;
  }

  public OrderDetails getDetails() {
    return details;
  }
}
