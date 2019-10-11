package com.lc.api.restaurant.resource;

import com.lc.api.restaurant.event.RecursoCreadoEvent;
import com.lc.api.restaurant.exceptionhandler.RestaurantExceptionHandler.Error;
import com.lc.api.restaurant.models.OrderItem;
import com.lc.api.restaurant.repository.OrderItemRepository;
import com.lc.api.restaurant.repository.filter.OrderItemFilter;
import com.lc.api.restaurant.service.OrderItemService;
import com.lc.api.restaurant.service.exception.ItemInexistenteException;
import com.lc.api.restaurant.service.exception.OrdenInexistenteException;
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
@RequestMapping("/order_item")
public class OrderItemResource {

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private OrderItemService orderItemService;

  @Autowired
  private MessageSource messageSource;


  @GetMapping
  public Page<OrderItem> buscar(OrderItemFilter orderItemFilter, Pageable pageable){
    return orderItemRepository.filtrar(orderItemFilter,pageable);
  }
  @GetMapping("/{codigo}")
  public ResponseEntity<OrderItem> buscarXId(@PathVariable Long codigo){
    Optional<OrderItem> itemOptional = orderItemRepository.findById(codigo);

    if(!itemOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(itemOptional.get());

  }

   @PostMapping
  public ResponseEntity<OrderItem> crear(@Valid @RequestBody OrderItem orderItem, HttpServletResponse response )
  {
    OrderItem orderItemSalvar = orderItemService.guardar(orderItem);

    publisher.publishEvent(new RecursoCreadoEvent(this, response, orderItemSalvar.getOrderItemId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(orderItemSalvar);
  }


  @PutMapping("/{codigo}")
  public ResponseEntity<OrderItem> actualizar(@PathVariable Long codigo,@Valid @RequestBody OrderItem orderItem){
    try{
      OrderItem orderItemSalva = orderItemService.actualizar(codigo,orderItem);
      return ResponseEntity.ok(orderItemSalva);
    }
    catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo){
    orderItemRepository.deleteById(codigo);
  }

// Y Listo solo falta probar las funciones

  @ExceptionHandler({OrdenInexistenteException.class})
  public ResponseEntity<Object> handleOrdenInexistenteException(OrdenInexistenteException ex)
  {
    String mensajeUsuario=messageSource.getMessage("orden.inexisten",null, LocaleContextHolder
        .getLocale());
    String mensajeDesarrollador= ExceptionUtils.getRootCauseMessage(ex);

    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return ResponseEntity.badRequest().body(errores);
  }

  @ExceptionHandler({ItemInexistenteException.class})
  public ResponseEntity<Object> handleItemInexistenteException(ItemInexistenteException ex)
  {
    String mensajeUsuario=messageSource.getMessage("item.inexisten",null, LocaleContextHolder
        .getLocale());
    String mensajeDesarrollador= ExceptionUtils.getRootCauseMessage(ex);

    List<Error> errores= Arrays.asList(new Error(mensajeUsuario,mensajeDesarrollador));

    return ResponseEntity.badRequest().body(errores);
  }
}
