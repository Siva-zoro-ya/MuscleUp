package com.muscleup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.muscleup.entity.Cart;
import com.muscleup.entity.User;
import com.muscleup.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //  Add Item to Cart
     @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error","Please log in first!");
            return "redirect:/users/login";
        }

        try {
            cartService.addToCart(user, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Item added to cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/home"; 
    }

    //  View Cart 
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        Cart cart = cartService.getCartWithItems(user);
        model.addAttribute("cart", cart);

        // Calculate total
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);

        return "cart";
    }

    //  Remove Item 
    @PostMapping("/remove")
    public String removeFromCart(
            @RequestParam Long productId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        cartService.removeFromCart(user, productId);
        redirectAttributes.addFlashAttribute("success", "Item removed!");
        return "redirect:/cart"; 
    }

    //  Update Quantity 
    @PostMapping("/update-quantity")
    public String updateQuantity(
            @RequestParam Long productId,
            @RequestParam int quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        try {
            cartService.updateQuantity(user, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Quantity updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    //Proceed to Checkout 
    @PostMapping("/checkout")
    public String checkout(
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        Cart cart = cartService.getCartWithItems(user);
        if (cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Cart is empty!");
            return "redirect:/cart";
        }

        return "redirect:/checkout"; 
    }
}
