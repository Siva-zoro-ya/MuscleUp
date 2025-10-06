package com.muscleup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muscleup.entity.Cart;
import com.muscleup.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user); // Fetch a user's cart
}
