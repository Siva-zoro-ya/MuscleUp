package com.muscleup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; 

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart; 

    private int quantity; 

   
    public CartItem() {} 

    public CartItem(Product product, Cart cart, int quantity) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }


    public Long getId() { 
    	return id; 
    	}
    public Product getProduct() { 
    	return product; 
    	}
    public Cart getCart() { 
    	return cart; 
    	}
    public int getQuantity() { 
    	return quantity; 
    	}

    public void setProduct(Product product) { 
    	this.product = product; 
    	}
    public void setCart(Cart cart) {
    	this.cart = cart; 
    	}
    public void setQuantity(int quantity) {
this.quantity = quantity; 
}
}
