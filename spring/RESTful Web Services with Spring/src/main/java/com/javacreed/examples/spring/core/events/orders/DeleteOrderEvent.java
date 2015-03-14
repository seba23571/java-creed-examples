package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.DeleteEvent;

public class DeleteOrderEvent extends DeleteEvent {

  private final UUID key;

  public DeleteOrderEvent(final UUID key) {
    this.key = key;
  }

  public UUID getKey() {
    return key;
  }
}
