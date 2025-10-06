package com.muscleup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muscleup.entity.Cart;
import com.muscleup.entity.CartItem;
import com.muscleup.entity.Order;
import com.muscleup.entity.User;
import com.muscleup.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService; 

    // Convert ALL cart items to orders 
    public void createOrdersFromCart(Cart cart, String deliveryAddress) {
        for (CartItem cartItem : cart.getItems()) {
            Order order = new Order(
                cart.getUser(),          
                cartItem.getProduct(),    
                cartItem.getQuantity(),   
                deliveryAddress           
            );
            orderRepository.save(order);  
        }
        cartService.clearCart(cart.getUser()); 
    }

    //  Fetch a user's order history 
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    //Update order status 
    public void updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
