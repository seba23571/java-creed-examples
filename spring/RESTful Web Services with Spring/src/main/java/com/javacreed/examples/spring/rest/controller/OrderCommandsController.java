package com.javacreed.examples.spring.rest.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.javacreed.examples.spring.core.events.orders.CreateOrderEvent;
import com.javacreed.examples.spring.core.events.orders.DeleteOrderEvent;
import com.javacreed.examples.spring.core.events.orders.OrderCreatedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDeletedEvent;
import com.javacreed.examples.spring.core.services.OrderService;
import com.javacreed.examples.spring.rest.domain.Order;

@Controller
@RequestMapping("/aggregators/orders")
public class OrderCommandsController {

  @SuppressWarnings("unused")
  private static Logger LOGGER = LoggerFactory.getLogger(OrderCommandsController.class);

  @Autowired
  private OrderService orderService;

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public ResponseEntity<Order> cancelOrder(@PathVariable final String id) {

    final OrderDeletedEvent orderDeleted = orderService.deleteOrder(new DeleteOrderEvent(UUID.fromString(id)));

    if (!orderDeleted.isEntityFound()) {
      return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
    }

    final Order order = Order.fromOrderDetails(orderDeleted.getDetails());

    if (orderDeleted.isDeletionCompleted()) {
      return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    return new ResponseEntity<Order>(order, HttpStatus.FORBIDDEN);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Order> createOrder(@RequestBody final Order order, final UriComponentsBuilder builder) {

    final OrderCreatedEvent orderCreated = orderService.createOrder(new CreateOrderEvent(order.toOrderDetails()));

    final Order newOrder = Order.fromOrderDetails(orderCreated.getDetails());

    final HttpHeaders headers = new HttpHeaders();
    headers.setLocation(builder.path("/aggregators/orders/{id}")
        .buildAndExpand(orderCreated.getNewOrderKey().toString()).toUri());

    return new ResponseEntity<Order>(newOrder, headers, HttpStatus.CREATED);
  }
}
