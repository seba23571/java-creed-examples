package com.javacreed.examples.spring.core.reporitory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.javacreed.examples.spring.core.domain.Order;

public class OrdersMemoryRepository implements OrdersRepository {

  private Map<UUID, Order> orders;

  public OrdersMemoryRepository(final Map<UUID, Order> orders) {
    this.orders = Collections.unmodifiableMap(orders);
  }

  @Override
  public synchronized void delete(final UUID key) {
    if (orders.containsKey(key)) {
      final Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
      modifiableOrders.remove(key);
      this.orders = Collections.unmodifiableMap(modifiableOrders);
    }
  }

  @Override
  public List<Order> findAll() {
    return Collections.unmodifiableList(new ArrayList<Order>(orders.values()));
  }

  @Override
  public Order findById(final UUID key) {
    return orders.get(key);
  }

  @Override
  public synchronized Order save(final Order order) {

    final Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
    modifiableOrders.put(order.getKey(), order);
    this.orders = Collections.unmodifiableMap(modifiableOrders);

    return order;
  }
}
