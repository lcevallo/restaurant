package com.lc.api.restaurant.repository.customer;

import com.lc.api.restaurant.models.Customer;
import com.lc.api.restaurant.repository.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryQuery {
    public Page<Customer> filtrar(CustomerFilter customerFilter, Pageable pageable);
}
