package com.muscleup.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // One cart per user

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>(); 

    // --- Constructors ---
    public Cart() {} 

    public Cart(User user) {
        this.user = user;
    }

    // --- Getters/Setters ---
    public Long getId() {
    	return id; 
    	}
    public User getUser() {
    	return user;
    	}
    public List<CartItem> getItems() { 
    	return items;
    	}

    public void setUser(User user) {
    	this.user = user; 
    	}


    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this); // Bidirectional sync
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null); // Bidirectional sync
    }

    // Calculate total 
    public double getTotal() {
        return items.stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }
}
