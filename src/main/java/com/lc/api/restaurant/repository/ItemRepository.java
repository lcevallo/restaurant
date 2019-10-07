package com.lc.api.restaurant.repository;

import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.repository.item.ItemRepositoryQuery;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository  extends JpaRepository<Item,Long>, ItemRepositoryQuery {
  Optional<Item> findByName(String name);
}
