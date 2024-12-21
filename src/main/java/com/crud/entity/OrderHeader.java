package com.crud.entity;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public
class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_contact_mech_id", nullable = false)
    private ContactMech shippingContact;

    @ManyToOne
    @JoinColumn(name = "billing_contact_mech_id", nullable = false)
    private ContactMech billingContact;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContactMech getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(ContactMech shippingContact) {
        this.shippingContact = shippingContact;
    }

    public ContactMech getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(ContactMech billingContact) {
        this.billingContact = billingContact;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}