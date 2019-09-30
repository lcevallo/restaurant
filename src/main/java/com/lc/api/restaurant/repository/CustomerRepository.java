package com.lc.api.restaurant.repository;

import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.repository.customer.CustomerRepositoryQuery;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> , CustomerRepositoryQuery {
   Optional<Customer> findByName(String name);

}
