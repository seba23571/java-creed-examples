package com.javacreed.examples.spring.rest.controller.fixture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.javacreed.examples.spring.core.events.orders.AllOrdersEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDetails;
import com.javacreed.examples.spring.rest.domain.Order;

//TODOCUMENT.  Use of test data fixture classes is considered good practice.
/*
 The majority of tests aren't testing data edge cases, they are testing logical flows and
 what happens to a generic set of data.  For these, use a small, standardised set of fixtures.

 For anything more esoteric, create a new fixture in the test class.
 */
public class RestDataFixture {

  public static final String YUMMY_ITEM = "yummy1";

  public static AllOrdersEvent allOrders() {
    final List<OrderDetails> orders = new ArrayList<OrderDetails>();

    orders.add(RestDataFixture.standardOrderDetails());
    orders.add(RestDataFixture.standardOrderDetails());
    orders.add(RestDataFixture.standardOrderDetails());

    return new AllOrdersEvent(orders);
  }

  public static OrderDetails customKeyOrderDetails(final UUID key) {
    final OrderDetails orderdetails = new OrderDetails(key);

    orderdetails.setOrderItems(Collections.singletonMap(RestDataFixture.YUMMY_ITEM, 12));

    return orderdetails;
  }

  public static Order standardOrder() {
    final Order order = new Order();

    order.setItems(Collections.singletonMap(RestDataFixture.YUMMY_ITEM, 12));

    return order;
  }

  public static OrderDetails standardOrderDetails() {
    return RestDataFixture.customKeyOrderDetails(UUID.randomUUID());
  }

  public static String standardOrderJSON() {
    return "{ \"items\": { \"yummy1\": 12, \"yummy15\": 42 } }";
  }
}