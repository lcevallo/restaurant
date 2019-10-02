package com.lc.api.restaurant.repository.Order;

import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.filter.OrderFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryQuery {
    public Page<Order> filtrar(OrderFilter orderFilter, Pageable pageable);
}
