package com.javacreed.examples.spring.core.events.orders;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class OrderDetails {

  private UUID key;
  private Date dateTimeOfSubmission;
  private Map<String, Integer> orderItems;

  public OrderDetails() {
    key = null;
  }

  public OrderDetails(final UUID key) {
    this.key = key;
  }

  public Date getDateTimeOfSubmission() {
    return this.dateTimeOfSubmission;
  }

  public UUID getKey() {
    return key;
  }

  public Map<String, Integer> getOrderItems() {
    return orderItems;
  }

  public void setDateTimeOfSubmission(final Date dateTimeOfSubmission) {
    this.dateTimeOfSubmission = dateTimeOfSubmission;
  }

  public void setKey(final UUID key) {
    this.key = key;
  }

  public void setOrderItems(final Map<String, Integer> orderItems) {
    if (orderItems == null) {
      this.orderItems = Collections.emptyMap();
    } else {
      this.orderItems = Collections.unmodifiableMap(orderItems);
    }
  }
}
