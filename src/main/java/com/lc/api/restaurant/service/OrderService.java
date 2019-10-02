package com.lc.api.restaurant.service;

import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.OrderRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {


  @Autowired
  private OrderRepository orderRepository;

  public Order guardar(@Valid Order order){
    Optional<Order> orderExistente = orderRepository.findByOrderNo(order.getOrderNo());

    if(orderExistente.isPresent()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Ya existe ese numero de Order");
    }
    return orderRepository.save(order);
  }


}
