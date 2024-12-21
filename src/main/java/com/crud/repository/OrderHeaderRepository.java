package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crud.entity.OrderHeader;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {}
