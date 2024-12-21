package com.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemSeqId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id", nullable = false)
    private OrderHeader orderHeader;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
    private String status;
    public Long getOrderItemSeqId() {
        return orderItemSeqId;
    }
    public void setOrderItemSeqId(Long orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }
    public OrderHeader getOrderHeader() {
        return orderHeader;
    }
    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}