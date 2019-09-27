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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  //TODO: funcion para agregar un nuevo Cliente
  @PostMapping
  public ResponseEntity<Customer> crear(@Valid @RequestBody Customer cliente, HttpServletResponse response){

    Customer clienteASalvar = customerService.guardar(cliente);
    publisher.publishEvent(new RecursoCreadoEvent(this, response, clienteASalvar.getCustomerId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(clienteASalvar);

  }
}
