package com.javacreed.examples.spring.core.events.orders;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.javacreed.examples.spring.core.events.ReadEvent;

public class AllOrdersEvent extends ReadEvent {

  private final List<OrderDetails> ordersDetails;

  public AllOrdersEvent(final List<OrderDetails> orders) {
    this.ordersDetails = Collections.unmodifiableList(orders);
  }

  public Collection<OrderDetails> getOrdersDetails() {
    return this.ordersDetails;
  }
}
