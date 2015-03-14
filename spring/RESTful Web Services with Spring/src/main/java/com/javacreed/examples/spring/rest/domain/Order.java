package com.javacreed.examples.spring.rest.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.javacreed.examples.spring.core.events.orders.OrderDetails;

public class Order implements Serializable {

  private static final long serialVersionUID = 4704202476227932214L;

  public static Order fromOrderDetails(final OrderDetails orderDetails) {
    final Order order = new Order();

    order.dateTimeOfSubmission = orderDetails.getDateTimeOfSubmission();
    order.key = orderDetails.getKey();
    order.setItems(orderDetails.getOrderItems());

    return order;
  }

  private Date dateTimeOfSubmission;

  private Map<String, Integer> items;

  private UUID key;

  public Date getDateTimeOfSubmission() {
    return dateTimeOfSubmission;
  }

  public Map<String, Integer> getItems() {
    return items;
  }

  public UUID getKey() {
    return key;
  }

  public void setDateTimeOfSubmission(final Date dateTimeOfSubmission) {
    this.dateTimeOfSubmission = dateTimeOfSubmission;
  }

  public void setItems(final Map<String, Integer> items) {
    if (items == null) {
      this.items = Collections.emptyMap();
    } else {
      this.items = Collections.unmodifiableMap(items);
    }
  }

  public void setKey(final UUID key) {
    this.key = key;
  }

  public OrderDetails toOrderDetails() {
    final OrderDetails details = new OrderDetails();

    details.setOrderItems(items);
    details.setKey(key);
    details.setDateTimeOfSubmission(dateTimeOfSubmission);

    return details;
  }
}