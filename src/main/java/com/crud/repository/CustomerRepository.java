package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crud.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
