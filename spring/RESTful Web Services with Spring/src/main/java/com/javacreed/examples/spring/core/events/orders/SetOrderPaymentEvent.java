package com.javacreed.examples.spring.core.events.orders;

import java.util.UUID;

import com.javacreed.examples.spring.core.events.UpdateEvent;

public class SetOrderPaymentEvent extends UpdateEvent {

  private final UUID key;
  private final PaymentDetails paymentDetails;

  public SetOrderPaymentEvent(final UUID key, final PaymentDetails paymentDetails) {
    this.key = key;
    this.paymentDetails = paymentDetails;
  }

  public UUID getKey() {
    return key;
  }

  public PaymentDetails getPaymentDetails() {
    return paymentDetails;
  }
}
