package com.javacreed.examples.spring.core.events.orders;

import java.util.Date;
import java.util.UUID;

public class PaymentDetails {

  private UUID key;
  private Date dateTimeOfSubmission;

  public PaymentDetails() {
    key = null;
  }

  public PaymentDetails(final UUID key) {
    this.key = key;
  }

  public Date getDateTimeOfSubmission() {
    return this.dateTimeOfSubmission;
  }

  public UUID getKey() {
    return key;
  }

  public void setDateTimeOfSubmission(final Date dateTimeOfSubmission) {
    this.dateTimeOfSubmission = dateTimeOfSubmission;
  }

  public void setKey(final UUID key) {
    this.key = key;
  }
}
