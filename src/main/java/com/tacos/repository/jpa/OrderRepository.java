package com.tacos.repository.jpa;

import com.tacos.data.Order;
import com.tacos.data.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlaceAtDesc(User user,Pageable pageable);
}