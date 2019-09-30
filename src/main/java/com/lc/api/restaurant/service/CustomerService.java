package com.lc.api.restaurant.service;

import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.repository.CustomerRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;


  public Customer guardar(@Valid Customer cliente) {
    Optional<Customer> clienteExistente = customerRepository.findByName(cliente.getName());

      if(clienteExistente.isPresent()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Ya existe un cliente con ese nombre en la base de datos");
      }
      return customerRepository.save(cliente);

  }

  public Customer actualizar(Long codigo, Customer customer) {
     Optional<Customer> customerOptional = customerRepository.findById(codigo);

      Customer customerSalva= customerOptional.get();
      BeanUtils.copyProperties( customer,customerSalva,"customerId");
      return customerRepository.save(customerSalva);

  }
}
