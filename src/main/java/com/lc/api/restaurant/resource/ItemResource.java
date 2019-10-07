package com.lc.api.restaurant.resource;

import com.lc.api.restaurant.event.RecursoCreadoEvent;
import com.lc.api.restaurant.models.Item;
import com.lc.api.restaurant.repository.ItemRepository;
import com.lc.api.restaurant.repository.filter.ItemFilter;
import com.lc.api.restaurant.service.ItemService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemResource {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ItemService itemService;

  @GetMapping
  public Page<Item> buscar(ItemFilter itemFilter, Pageable pageable){
    return itemRepository.filtrar(itemFilter,pageable);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Item> buscarXId(@PathVariable Long codigo){
    Optional<Item> itemOptional = itemRepository.findById(codigo);

    if(!itemOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(itemOptional.get());

  }


  @PostMapping
  public ResponseEntity<Item> crear(@Valid @RequestBody Item item, HttpServletResponse response )
  {
    Item itemSalvar = itemService.guardar(item);

    publisher.publishEvent(new RecursoCreadoEvent(this, response, itemSalvar.getItemId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvar);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<Item> actualizar(@PathVariable Long codigo,@Valid @RequestBody Item item){
    try{
      Item itemSalva = itemService.actualizar(codigo,item);
      return ResponseEntity.ok(itemSalva);
    }
    catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }
  }



  @DeleteMapping("{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo)
  {
    itemRepository.deleteById(codigo);
  }

  //Listo todo el CRUD de ITEMS Gracias

}
