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
import com.muscleup.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    //  Show Checkout Form (Delivery Address)
     @GetMapping
    public String showCheckoutForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        Cart cart = cartService.getCartWithItems(user);
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart"; 
        }

        model.addAttribute("cart", cart);
        return "checkout"; 
    }

    //  Process Order
    @PostMapping
    public String processCODOrder(
            @RequestParam String deliveryAddress,
            HttpSession session,
            RedirectAttributes redirectAttrs
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        Cart cart = cartService.getCartWithItems(user);

        // Convert cart items to Order entities (one order per item)
        orderService.createOrdersFromCart(cart, deliveryAddress);

        // Clear the cart
        cartService.clearCart(user); 

        // Pass order details to confirmation page
        redirectAttrs.addFlashAttribute("orderTotal", cart.getTotal());
        redirectAttrs.addFlashAttribute("deliveryAddress", deliveryAddress);

        return "redirect:/order-confirm"; 
    }
}
