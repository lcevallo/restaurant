package com.lc.api.restaurant.resource;


import com.lc.api.restaurant.event.RecursoCreadoEvent;
import com.lc.api.restaurant.exceptionhandler.RestaurantExceptionHandler.Error;
import com.lc.api.restaurant.models.Order;
import com.lc.api.restaurant.repository.OrderRepository;
import com.lc.api.restaurant.repository.filter.OrderFilter;
import com.lc.api.restaurant.service.OrderItemService;
import com.lc.api.restaurant.service.OrderService;
import com.lc.api.restaurant.service.exception.ClienteInexistenteException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @Autowired
  private OrderItemService orderItemService;

  @Autowired
  private MessageSource messageSource;

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


  @PostMapping
  public ResponseEntity<Order> crear(@Valid @RequestBody Order order, HttpServletResponse response )
  {
    Order orderSalvar = orderService.guardar(order);
    order.getOrderItems().stream().forEach(
        orden -> {
          order.setOrderId(orderSalvar.getOrderId());
          this.orderItemService.guardar(orden);
        }
    );

    Order orderResult = this.orderRepository.findById(orderSalvar.getOrderId()).get();

    publisher.publishEvent(new RecursoCreadoEvent(this, response, orderResult.getOrderId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(orderResult);
  }

  @ExceptionHandler({ClienteInexistenteException.class})
  public ResponseEntity<Object> handleClienteInexistenteException(ClienteInexistenteException ex)
  {
    String mensajeUsuario=messageSource.getMessage("cliente.inexisten",null, LocaleContextHolder
        .getLocale());
    String mensajeDesarrollador= ExceptionUtils.getRootCauseMessage(ex);
    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));
    return ResponseEntity.badRequest().body(errores);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Order> actualizar(@PathVariable Long codigo,@Valid @RequestBody Order order){
    try{
      Order orderSalva = orderService.actualizar(codigo,order);
      return ResponseEntity.ok(orderSalva);
    }
    catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }
  }


  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo){
    orderRepository.deleteById(codigo);
  }


}
