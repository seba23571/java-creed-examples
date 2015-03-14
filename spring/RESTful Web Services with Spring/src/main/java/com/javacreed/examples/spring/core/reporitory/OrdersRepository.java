package com.javacreed.examples.spring.core.reporitory;

import java.util.List;
import java.util.UUID;

import com.javacreed.examples.spring.core.domain.Order;

//TODO, make this event based again, with persistence integration events.
public interface OrdersRepository {

  void delete(UUID key);

  List<Order> findAll();

  Order findById(UUID key);

  Order save(Order order);
}
