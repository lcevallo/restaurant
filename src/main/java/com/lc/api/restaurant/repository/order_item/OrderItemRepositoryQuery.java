package com.lc.api.restaurant.repository.order_item;

import com.lc.api.restaurant.models.OrderItem;
import com.lc.api.restaurant.repository.filter.OrderItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryQuery {
  public Page<OrderItem> filtrar(OrderItemFilter orderItemFilter, Pageable pageable);

}
