package com.muscleup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.muscleup.entity.Order;
import com.muscleup.entity.User;
import com.muscleup.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //order confirm and details
    @GetMapping("/confirm")
    public String showOrderConfirmation() {
        return "order-confirm"; 
    }

    //  Order History Page
    @GetMapping("/history")
    public String showOrderHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/users/login";
        }

        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "order-history"; 
    }
}
