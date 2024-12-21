package com.crud.controller;

import com.crud.entity.*;
import com.crud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderHeader> createOrder(@RequestBody OrderHeader orderHeader) {
        if (orderHeader.getCustomer() == null || orderHeader.getCustomer().getCustomer_id() == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        if (orderHeader.getShippingContact() == null || orderHeader.getShippingContact().getContactMechId() == null) {
            throw new IllegalArgumentException("Shipping Contact ID must not be null");
        }
        if (orderHeader.getBillingContact() == null || orderHeader.getBillingContact().getContactMechId() == null) {
            throw new IllegalArgumentException("Billing Contact ID must not be null");
        }

        // Service call
        OrderHeader createdOrder = orderService.createOrder(orderHeader);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderHeader> getOrderById(@PathVariable Long orderId) {
        Optional<OrderHeader> order = orderService.getOrderById(orderId);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderHeader> updateOrder(@PathVariable Long orderId, @RequestBody OrderHeader orderHeader) {
        OrderHeader updatedOrder = orderService.updateOrder(orderId, orderHeader);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order Deleted Successfully");
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItem> addOrderItem(@PathVariable Long orderId, @RequestBody OrderItem orderItem) {
        OrderItem createdItem = orderService.addOrderItem(orderId, orderItem);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<OrderItem> updateOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long orderItemSeqId,
            @RequestBody OrderItem orderItem) {
        OrderItem updatedItem = orderService.updateOrderItem(orderId, orderItemSeqId, orderItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<String> deleteOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long orderItemSeqId) {
        orderService.deleteOrderItem(orderId, orderItemSeqId);
        return ResponseEntity.ok("Item deleted from Order");
    }

}
