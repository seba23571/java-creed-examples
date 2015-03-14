package com.javacreed.examples.spring.rest.controller.fixture;

import java.util.Date;
import java.util.UUID;

import com.javacreed.examples.spring.core.events.orders.OrderCreatedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDeletedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDetailsEvent;
import com.javacreed.examples.spring.core.events.orders.OrderStatusDetails;
import com.javacreed.examples.spring.core.events.orders.OrderStatusEvent;

public class RestEventFixtures {
  public static OrderCreatedEvent orderCreated(final UUID key) {
    return new OrderCreatedEvent(key, RestDataFixture.customKeyOrderDetails(key));
  }

  public static OrderDeletedEvent orderDeleted(final UUID key) {
    return new OrderDeletedEvent(key, RestDataFixture.standardOrderDetails());
  }

  public static OrderDeletedEvent orderDeletedFailed(final UUID key) {
    return OrderDeletedEvent.deletionForbidden(key, RestDataFixture.standardOrderDetails());
  }

  public static OrderDeletedEvent orderDeletedNotFound(final UUID key) {
    return OrderDeletedEvent.notFound(key);
  }

  public static OrderDetailsEvent orderDetailsEvent(final UUID key) {
    return new OrderDetailsEvent(key, RestDataFixture.customKeyOrderDetails(key));
  }

  public static OrderDetailsEvent orderDetailsNotFound(final UUID key) {
    return OrderDetailsEvent.notFound(key);
  }

  public static OrderStatusEvent orderStatus(final UUID key, final String status) {
    return new OrderStatusEvent(key, new OrderStatusDetails(new Date(), status));
  }

  public static OrderStatusEvent orderStatusNotFound(final UUID key) {
    return OrderStatusEvent.notFound(key);
  }
}
