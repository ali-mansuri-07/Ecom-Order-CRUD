package com.crud.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
