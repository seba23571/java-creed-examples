package com.javacreed.examples.spring.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javacreed.examples.spring.core.domain.Order;
import com.javacreed.examples.spring.core.domain.OrderStatus;
import com.javacreed.examples.spring.core.events.orders.AllOrdersEvent;
import com.javacreed.examples.spring.core.events.orders.CreateOrderEvent;
import com.javacreed.examples.spring.core.events.orders.DeleteOrderEvent;
import com.javacreed.examples.spring.core.events.orders.OrderCreatedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDeletedEvent;
import com.javacreed.examples.spring.core.events.orders.OrderDetails;
import com.javacreed.examples.spring.core.events.orders.OrderDetailsEvent;
import com.javacreed.examples.spring.core.events.orders.OrderStatusEvent;
import com.javacreed.examples.spring.core.events.orders.OrderUpdatedEvent;
import com.javacreed.examples.spring.core.events.orders.RequestAllOrdersEvent;
import com.javacreed.examples.spring.core.events.orders.RequestOrderDetailsEvent;
import com.javacreed.examples.spring.core.events.orders.RequestOrderStatusEvent;
import com.javacreed.examples.spring.core.events.orders.SetOrderPaymentEvent;
import com.javacreed.examples.spring.core.reporitory.OrdersRepository;

public class OrderEventHandler implements OrderService {

  private final OrdersRepository ordersRepository;

  public OrderEventHandler(final OrdersRepository ordersRepository) {
    this.ordersRepository = ordersRepository;
  }

  @Override
  public OrderCreatedEvent createOrder(final CreateOrderEvent createOrderEvent) {
    Order order = Order.fromOrderDetails(createOrderEvent.getDetails());

    order.addStatus(new OrderStatus(new Date(), "Order Created"));

    order = ordersRepository.save(order);

    return new OrderCreatedEvent(order.getKey(), order.toOrderDetails());
  }

  @Override
  public OrderDeletedEvent deleteOrder(final DeleteOrderEvent deleteOrderEvent) {

    final Order order = ordersRepository.findById(deleteOrderEvent.getKey());

    if (order == null) {
      return OrderDeletedEvent.notFound(deleteOrderEvent.getKey());
    }

    final OrderDetails details = order.toOrderDetails();

    // TODOCUMENT This contains some specific domain logic, not exposed to the outside world, and not part of the
    // persistence rules.

    if (!order.canBeDeleted()) {
      return OrderDeletedEvent.deletionForbidden(deleteOrderEvent.getKey(), details);
    }

    ordersRepository.delete(deleteOrderEvent.getKey());
    return new OrderDeletedEvent(deleteOrderEvent.getKey(), details);
  }

  @Override
  public AllOrdersEvent requestAllOrders(final RequestAllOrdersEvent requestAllCurrentOrdersEvent) {
    final List<OrderDetails> generatedDetails = new ArrayList<OrderDetails>();
    for (final Order order : ordersRepository.findAll()) {
      generatedDetails.add(order.toOrderDetails());
    }
    return new AllOrdersEvent(generatedDetails);
  }

  @Override
  public OrderDetailsEvent requestOrderDetails(final RequestOrderDetailsEvent requestOrderDetailsEvent) {

    final Order order = ordersRepository.findById(requestOrderDetailsEvent.getKey());

    if (order == null) {
      return OrderDetailsEvent.notFound(requestOrderDetailsEvent.getKey());
    }

    return new OrderDetailsEvent(requestOrderDetailsEvent.getKey(), order.toOrderDetails());
  }

  @Override
  public OrderStatusEvent requestOrderStatus(final RequestOrderStatusEvent requestOrderDetailsEvent) {
    final Order order = ordersRepository.findById(requestOrderDetailsEvent.getKey());

    if (order == null) {
      return OrderStatusEvent.notFound(requestOrderDetailsEvent.getKey());
    }

    return new OrderStatusEvent(requestOrderDetailsEvent.getKey(), order.getStatus().toStatusDetails());
  }

  @Override
  public OrderUpdatedEvent setOrderPayment(final SetOrderPaymentEvent setOrderPaymentEvent) {
    return null; // To change body of implemented methods use File | Settings | File Templates.
  }
}
