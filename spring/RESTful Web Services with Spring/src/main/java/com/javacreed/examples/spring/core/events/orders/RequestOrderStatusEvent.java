package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.RequestReadEvent;

public class RequestOrderStatusEvent extends RequestReadEvent {
  private final UUID key;

  public RequestOrderStatusEvent(final UUID key) {
    this.key = key;
  }

  public UUID getKey() {
    return key;
  }
}
