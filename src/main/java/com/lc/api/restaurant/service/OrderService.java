package com.lc.api.restaurant.service;

import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.CustomerRepository;
import com.lc.api.restaurant.repository.OrderRepository;
import com.lc.api.restaurant.service.exception.ClienteInexistenteException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {


  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public Order guardar(@Valid Order order){
    Optional<Order> orderExistente = orderRepository.findByOrderNo(order.getOrderNo());

    if(orderExistente.isPresent()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Ya existe ese numero de Order");
    }
    if(order.getCustomer().getCustomerId()==null || StringUtils.isEmpty(order.getCustomer().getCustomerId())){
      throw new ClienteInexistenteException();
    }

    Customer customer = orderExistente.get().getCustomer();

    Optional<Customer> customerOptional = this.customerRepository.findById(customer.getCustomerId());

    if(!customerOptional.isPresent()){
      throw new ClienteInexistenteException();
    }
    Order orderG = new Order();
    BeanUtils.copyProperties(order,orderG,"OrderItem");
    return orderRepository.save(orderG);
  }


  public Order actualizar(Long codigo, Order order) {
    Optional<Order> orderOptional = orderRepository.findById(codigo);

   /* if(!orderOptional.isPresent()){

    }*/
    Order orderSalva = orderOptional.get();

    if (order.getCustomer().getCustomerId()==null || StringUtils.isEmpty(order.getCustomer().getCustomerId())){
      throw new ClienteInexistenteException();
    }
    Optional<Customer> customerSalva = customerRepository.findById(order.getCustomer().getCustomerId());

    if(!customerSalva.isPresent()){
      throw new ClienteInexistenteException();
    }

    BeanUtils.copyProperties(order,orderSalva,"orderId");

    return orderRepository.save(orderSalva);

  }
}
