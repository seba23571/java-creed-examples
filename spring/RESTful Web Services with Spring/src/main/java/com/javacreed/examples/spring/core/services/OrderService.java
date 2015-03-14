package com.javacreed.examples.spring.core.services;

import com.javacreed.examples.spring.core.events.orders.AllOrdersEvent;
import com.javacreed.examples.spring.core.events.orders.CreateOrderEvent;
import com.javacreed.examples.spring.core.events.orders.DeleteOrderEvent;
import com.javacreed.examples.spring.core.events.orders.OrderCreatedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDeletedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDetailsEvent;
import com.javacreed.examples.spring.core.events.orders.OrderStatusEvent;
import com.javacreed.examples.spring.core.events.orders.OrderUpdatedEvent;
import com.javacreed.examples.spring.core.events.orders.RequestAllOrdersEvent;
import com.javacreed.examples.spring.core.events.orders.RequestOrderDetailsEvent;
import com.javacreed.examples.spring.core.events.orders.RequestOrderStatusEvent;
import com.javacreed.examples.spring.core.events.orders.SetOrderPaymentEvent;

//TODOCUMENT This is an event driven service.
// Used to interact with the core domain.
//All methods are guaranteed to return something, null will never be returned.
public interface OrderService {

  public OrderCreatedEvent createOrder(CreateOrderEvent event);

  public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent);

  public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent);

  public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent);

  public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderStatusEvent);

  public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent);
}
