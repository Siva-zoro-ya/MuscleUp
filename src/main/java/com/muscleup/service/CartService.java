package com.muscleup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muscleup.entity.Cart;
import com.muscleup.entity.CartItem;
import com.muscleup.entity.Product;
import com.muscleup.entity.User;
import com.muscleup.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    // Add item to cart (or update quantity if already exists)
    public Cart addToCart(User user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found!"); // Example error handling
        }
        // Check if product already in cart
        CartItem existingItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            // Update quantity
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add new item
            CartItem newItem = new CartItem(product, cart, quantity);
            cart.addItem(newItem);
        }

        return cartRepository.save(cart); 
    }

    // Get user's cart or create if none exists
    public Cart getOrCreateCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    // Remove item from cart
    public void removeFromCart(User user, Long productId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    // Fetch user's cart with items
    public Cart getCartWithItems(User user) {
        return cartRepository.findByUser(user);
    }

    //for quantity update in cart
    public void updateQuantity(User user, Long productId, int newQuantity) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cart.getItems().stream()
            .filter(i -> i.getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));

        if (newQuantity <= 0) {
            removeFromCart(user, productId); 
        } else {
            item.setQuantity(newQuantity); 
            cartRepository.save(cart);
        }
    }

 //to clear cart
    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear(); 
        cartRepository.save(cart);
    }


}
