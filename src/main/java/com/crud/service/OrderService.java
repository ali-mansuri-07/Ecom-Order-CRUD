package com.crud.service;

import com.crud.entity.*;
import com.crud.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContactMechRepository contactMechRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderHeader createOrder(OrderHeader orderHeader) {
        if (orderHeader.getCustomer() == null || orderHeader.getCustomer().getCustomer_id() == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        if (orderHeader.getShippingContact() == null || orderHeader.getShippingContact().getContactMechId() == null) {
            throw new IllegalArgumentException("Shipping Contact ID must not be null");
        }
        if (orderHeader.getBillingContact() == null || orderHeader.getBillingContact().getContactMechId() == null) {
            throw new IllegalArgumentException("Billing Contact ID must not be null");
        }
    
        Customer customer = customerRepository.findById(orderHeader.getCustomer().getCustomer_id())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        orderHeader.setCustomer(customer);
    
        ContactMech shippingContact = contactMechRepository.findById(orderHeader.getShippingContact().getContactMechId())
                .orElseThrow(() -> new EntityNotFoundException("Shipping contact not found"));
        orderHeader.setShippingContact(shippingContact);
    
        ContactMech billingContact = contactMechRepository.findById(orderHeader.getBillingContact().getContactMechId())
                .orElseThrow(() -> new EntityNotFoundException("Billing contact not found"));
        orderHeader.setBillingContact(billingContact);
    
        for (OrderItem item : orderHeader.getOrderItems()) {
            if (item.getProduct() == null || item.getProduct().getProduct_id() == null) {
                throw new IllegalArgumentException("Product ID in order item must not be null");
            }
            item.setOrderHeader(orderHeader);
        }
    
        return orderHeaderRepository.save(orderHeader);
    }
    

    public Optional<OrderHeader> getOrderById(Long orderId) {
        return orderHeaderRepository.findById(orderId);
    }

    public OrderHeader updateOrder(Long orderId, OrderHeader updatedOrderHeader) {
        OrderHeader existingOrder = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    
        // Update Customer
        if (updatedOrderHeader.getCustomer() != null && updatedOrderHeader.getCustomer().getCustomer_id() != null) {
            Customer customer = customerRepository.findById(updatedOrderHeader.getCustomer().getCustomer_id())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existingOrder.setCustomer(customer);
        }
    
        // Update Shipping Contact
        if (updatedOrderHeader.getShippingContact() != null && updatedOrderHeader.getShippingContact().getContactMechId() != null) {
            ContactMech shippingContact = contactMechRepository.findById(updatedOrderHeader.getShippingContact().getContactMechId())
                    .orElseThrow(() -> new EntityNotFoundException("Shipping contact not found"));
            existingOrder.setShippingContact(shippingContact);
        }
    
        // Update Billing Contact
        if (updatedOrderHeader.getBillingContact() != null && updatedOrderHeader.getBillingContact().getContactMechId() != null) {
            ContactMech billingContact = contactMechRepository.findById(updatedOrderHeader.getBillingContact().getContactMechId())
                    .orElseThrow(() -> new EntityNotFoundException("Billing contact not found"));
            existingOrder.setBillingContact(billingContact);
        }
    
        // Update Order Items
        if (updatedOrderHeader.getOrderItems() != null && !updatedOrderHeader.getOrderItems().isEmpty()) {
            // Clear existing order items
            existingOrder.getOrderItems().clear();
    
            // Add updated order items
            for (OrderItem item : updatedOrderHeader.getOrderItems()) {
                if (item.getProduct() == null || item.getProduct().getProduct_id() == null) {
                    throw new IllegalArgumentException("Product ID in order item must not be null");
                }
    
                Product product = productRepository.findById(item.getProduct().getProduct_id())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
                item.setProduct(product);
                item.setOrderHeader(existingOrder);
                existingOrder.getOrderItems().add(item);
            }
        }
    
        // Save and return updated order
        return orderHeaderRepository.save(existingOrder);
    }
    

    public void deleteOrder(Long orderId) {
        if (!orderHeaderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderHeaderRepository.deleteById(orderId);
    }

    public OrderItem addOrderItem(Long orderId, OrderItem orderItem) {
        OrderHeader orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderItem.setOrderHeader(orderHeader);
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long orderId, Long orderItemSeqId, OrderItem updatedOrderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        // Update details
        existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
        existingOrderItem.setStatus(updatedOrderItem.getStatus());

        return orderItemRepository.save(existingOrderItem);
    }

    public void deleteOrderItem(Long orderId, Long orderItemSeqId) {
        OrderItem existingOrderItem = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));
        orderItemRepository.delete(existingOrderItem);
    }
}
