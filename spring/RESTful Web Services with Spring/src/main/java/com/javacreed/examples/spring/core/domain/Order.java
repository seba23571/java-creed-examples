package com.javacreed.examples.spring.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.javacreed.examples.spring.core.events.orders.OrderDetails;

public class Order {

  public static Order fromOrderDetails(final OrderDetails orderDetails) {
    final Order order = new Order(orderDetails.getDateTimeOfSubmission());

    BeanUtils.copyProperties(orderDetails, order);

    return order;
  }

  private final Date dateTimeOfSubmission;
  private Map<String, Integer> orderItems;
  private final UUID key;

  @SuppressWarnings("unused")
  private Customer customer;
  private OrderStatus status;

  private final List<OrderStatus> statusHistory;

  public Order(final Date dateTimeOfSubmission) {
    this.key = UUID.randomUUID();
    this.dateTimeOfSubmission = dateTimeOfSubmission;
    statusHistory = new ArrayList<OrderStatus>();
  }

  public void addStatus(final OrderStatus newStatus) {
    statusHistory.add(newStatus);
    status = newStatus;
  }

  public boolean canBeDeleted() {
    return true;
  }

  public Date getDateTimeOfSubmission() {
    return dateTimeOfSubmission;
  }

  public UUID getKey() {
    return key;
  }

  public Map<String, Integer> getOrderItems() {
    return orderItems;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setOrderItems(final Map<String, Integer> orderItems) {
    if (orderItems == null) {
      this.orderItems = Collections.emptyMap();
    } else {
      this.orderItems = Collections.unmodifiableMap(orderItems);
    }
  }

  public OrderDetails toOrderDetails() {
    final OrderDetails details = new OrderDetails();

    BeanUtils.copyProperties(this, details);

    return details;
  }
}
