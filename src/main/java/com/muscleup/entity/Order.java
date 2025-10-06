package com.muscleup.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; 

    private int quantity; 

    private String deliveryAddress; 
    private double priceAtOrder;

    private String status; 

    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public Order(User user, Product product, int quantity, String deliveryAddress) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.priceAtOrder = product.getPrice(); 
        this.status = "PENDING"; 
    }

    
    public Long getId() {
    	return id; 
    	}
    public User getUser() {
    	return user; 
    	}
    public Product getProduct() {
    	return product; 
    	}
    public int getQuantity() {
    	return quantity; 
    	}
    public String getDeliveryAddress() {
    	return deliveryAddress; 
    	}
    public double getPriceAtOrder() {
    	return priceAtOrder;
    	}
    public String getStatus() {
    	return status; 
    	}
    public LocalDateTime getOrderDate() {
    	return orderDate; 
    	}

 
    public void setStatus(String status) {
    	this.status = status; 
    	}
}
