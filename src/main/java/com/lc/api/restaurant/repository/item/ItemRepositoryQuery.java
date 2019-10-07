package com.lc.api.restaurant.repository.item;

import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.repository.filter.ItemFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryQuery {
  public Page<Item> filtrar(ItemFilter itemFilter, Pageable pageable);
}
