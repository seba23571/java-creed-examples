package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.RequestReadEvent;

public class RequestOrderDetailsEvent extends RequestReadEvent {
  private final UUID key;

  public RequestOrderDetailsEvent(final UUID key) {
    this.key = key;
  }

  public UUID getKey() {
    return key;
  }
}
