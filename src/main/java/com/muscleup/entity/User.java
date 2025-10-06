package com.muscleup.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password; 
    private String name;
    private String mobile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart; // User's cart

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>(); 

    
    public User() {} 

    public User(String email, String password, String name, String mobile) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

  
    public Long getId() {
    	return id;
    	}

    public void setId(Long id) {
    	this.id = id;
    	}

    public String getEmail() {
    	return email;
    	}

    public void setEmail(String email) {
    	this.email = email;
    	}

    public String getPassword() {
    	return password;
    	}

    public void setPassword(String password) {
    	this.password = password;
    	}

    public String getName() {
    	return name;
    	}

    public void setName(String name) {
    	this.name = name;
    	}


    public String getMobile() {
    	return mobile;
    	}

    public void setMobile(String mobile) {
    	this.mobile = mobile;
    	}


    public Cart getCart() {
    	return cart;
    	}

    public void setCart(Cart cart) {
        if (cart == null) {
            if (this.cart != null) {
                this.cart.setUser (null); 
            }
        } else {
            cart.setUser (this); 
        }
        this.cart = cart;
    }

    public List<Order> getOrders() {
    	return orders;
    	}

    public void setOrders(List<Order> orders) {
    	this.orders = orders;
    	}
}
