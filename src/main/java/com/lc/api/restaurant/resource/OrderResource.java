package com.lc.api.restaurant.resource;


import com.lc.api.restaurant.event.RecursoCreadoEvent;
import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.OrderRepository;
import com.lc.api.restaurant.repository.filter.OrderFilter;
import com.lc.api.restaurant.service.OrderService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderResource {
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private OrderService orderService;

  @GetMapping
  public Page<Order> buscar(OrderFilter orderFilter, Pageable pageable){
    return orderRepository.filtrar(orderFilter,pageable);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Order> buscarXId(@PathVariable Long codigo){
    Optional<Order> orderOptional = orderRepository.findById(codigo);

    if(!orderOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(orderOptional.get());

  }

  //TODO: Tengo que tratar ciertos eventos como por ejemplo que coloque un id de cliente valido
  @PostMapping
  public ResponseEntity<Order> crear(@Valid @RequestBody Order order, HttpServletResponse response )
  {
    Order orderSalvar = orderService.guardar(order);

    publisher.publishEvent(new RecursoCreadoEvent(this, response, orderSalvar.getOrderId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(orderSalvar);
  }


}
