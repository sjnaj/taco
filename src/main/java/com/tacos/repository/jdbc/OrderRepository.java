package com.tacos.repository.jdbc;

import com.tacos.data.Order;

public interface OrderRepository {
  Order save(Order order);
}
