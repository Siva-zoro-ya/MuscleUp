package com.muscleup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muscleup.entity.Order;
import com.muscleup.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders by a user (for "My Orders" page)
    List<Order> findByUser(User user); 

 
}
