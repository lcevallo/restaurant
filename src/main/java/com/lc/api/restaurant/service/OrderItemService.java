package com.lc.api.restaurant.service;

import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.models.OrderItem;
import com.lc.api.restaurant.repository.ItemRepository;
import com.lc.api.restaurant.repository.OrderItemRepository;
import com.lc.api.restaurant.repository.OrderRepository;
import com.lc.api.restaurant.service.exception.ItemInexistenteException;
import com.lc.api.restaurant.service.exception.OrdenInexistenteException;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OrderItemService {

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ItemRepository itemRepository;


  //Ahora tengo que manejar estas exceptions dentro de resources
  public OrderItem guardar(OrderItem orderItem) {


    if(orderItem.getItemId().getItemId()==null || StringUtils.isEmpty(orderItem.getItemId().getItemId())){
      throw new ItemInexistenteException();
    }

    Item item = orderItem.getItemId();

    Optional<Item> itemOptional = this.itemRepository.findById(item.getItemId());

    if(!itemOptional.isPresent()){
      throw new ItemInexistenteException();
    }

    if(orderItem.getOrderId().getOrderId()==null || StringUtils.isEmpty(orderItem.getOrderId().getOrderId())){
      throw new OrdenInexistenteException();
    }

    Order order = orderItem.getOrderId();

    Optional<Order> orderOptional = this.orderRepository.findById(order.getOrderId());

    if(!orderOptional.isPresent()){
      throw new OrdenInexistenteException();
    }

    return orderItemRepository.save(orderItem);

  }

  public OrderItem actualizar(Long codigo, OrderItem orderItem) {
    Optional<OrderItem> orderItemOptional = orderItemRepository.findById(codigo);

    OrderItem orderItemSalva = orderItemOptional.get();

    if (orderItem.getItemId().getItemId()==null || StringUtils.isEmpty(orderItem.getItemId().getItemId())){
      throw new ItemInexistenteException();
    }
    Optional<Item> itemSalva = itemRepository.findById(orderItem.getItemId().getItemId());

    if(!itemSalva.isPresent()){
      throw new ItemInexistenteException();
    }

    //Seccion Orden

    if (orderItem.getOrderId().getOrderId()==null || StringUtils.isEmpty(orderItem.getOrderId().getOrderId())){
      throw new OrdenInexistenteException();
    }
    Optional<Order> orderSalva = orderRepository.findById(orderItem.getOrderId().getOrderId());

    if(!orderSalva.isPresent()){
      throw new OrdenInexistenteException();
    }

    BeanUtils.copyProperties(orderItem,orderItemSalva,"orderItemId");

    return orderItemRepository.save(orderItemSalva);

  }
}
