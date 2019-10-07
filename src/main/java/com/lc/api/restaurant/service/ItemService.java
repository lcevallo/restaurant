package com.lc.api.restaurant.service;

import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.repository.ItemRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ItemService {

  @Autowired
  private ItemRepository itemRepository;

  public Item guardar(@Valid Item item){

    Optional<Item> itemExistente = itemRepository.findByName(item.getName());

    if(itemExistente.isPresent()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Ya existe ese nombre de item");
    }


    return itemRepository.save(item);
  }


  public Item actualizar(Long codigo, Item item) {
    Optional<Item> itemOptional = itemRepository.findById(codigo);

    Item itemSalva= itemOptional.get();
    BeanUtils.copyProperties( item,itemSalva,"itemId");
    return itemRepository.save(itemSalva);

  }
}
