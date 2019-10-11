package com.lc.api.restaurant.repository;

import com.lc.api.restaurant.models.OrderItem;
import com.lc.api.restaurant.repository.order_item.OrderItemRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,
    OrderItemRepositoryQuery {

}
