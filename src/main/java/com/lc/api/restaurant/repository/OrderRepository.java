package com.lc.api.restaurant.repository;

import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.Order.OrderRepositoryQuery;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long>, OrderRepositoryQuery {
  Optional<Order> findByOrderNo(String orderNo);
}
