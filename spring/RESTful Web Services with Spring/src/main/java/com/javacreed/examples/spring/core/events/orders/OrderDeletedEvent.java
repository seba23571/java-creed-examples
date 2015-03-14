package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.DeletedEvent;

public class OrderDeletedEvent extends DeletedEvent {

  public static OrderDeletedEvent deletionForbidden(final UUID key, final OrderDetails details) {
    final OrderDeletedEvent ev = new OrderDeletedEvent(key, details);
    ev.entityFound = true;
    ev.deletionCompleted = false;
    return ev;
  }

  public static OrderDeletedEvent notFound(final UUID key) {
    final OrderDeletedEvent ev = new OrderDeletedEvent(key);
    ev.entityFound = false;
    return ev;
  }

  private final UUID key;

  private OrderDetails details;

  private boolean deletionCompleted;

  private OrderDeletedEvent(final UUID key) {
    this.key = key;
  }

  public OrderDeletedEvent(final UUID key, final OrderDetails details) {
    this.key = key;
    this.details = details;
    this.deletionCompleted = true;
  }

  public OrderDetails getDetails() {
    return details;
  }

  public UUID getKey() {
    return key;
  }

  public boolean isDeletionCompleted() {
    return deletionCompleted;
  }
}
