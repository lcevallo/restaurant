package com.lc.api.restaurant.resource;

import com.lc.api.restaurant.event.RecursoCreadoEvent;
import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.repository.CustomerRepository;
import com.lc.api.restaurant.repository.filter.CustomerFilter;
import com.lc.api.restaurant.service.CustomerService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
@RequestMapping("/customer")
public class CustomerResource {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ApplicationEventPublisher publisher;


  @GetMapping
  public Page<Customer> buscar(CustomerFilter customerFilter, Pageable pageable){
    return customerRepository.filtrar(customerFilter,pageable);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Customer> buscarxId(@PathVariable Long codigo){

    Optional<Customer> cliente = customerRepository.findById(codigo);

    if(!cliente.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(cliente.get());

  }


  @PostMapping
  public ResponseEntity<Customer> crear(@Valid @RequestBody Customer cliente, HttpServletResponse response){

    Customer clienteASalvar = customerService.guardar(cliente);
    publisher.publishEvent(new RecursoCreadoEvent(this, response, clienteASalvar.getCustomerId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteASalvar);

  }

  //TODO: Funcion para actualizar a un nuevo cliente
  @PutMapping("/{codigo}")
  public ResponseEntity<Customer> actualizar(@PathVariable Long codigo,@Valid @RequestBody Customer customer )
  {
    try {
      Customer customerSalvar = customerService.actualizar(codigo, customer);
      return ResponseEntity.ok(customerSalvar);
    }
    catch (IllegalArgumentException e){
      return ResponseEntity.notFound().build();
    }

  }

  //La ultima operacion DELETE
  @DeleteMapping("{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo)
  {
    customerRepository.deleteById(codigo);
  }


}
