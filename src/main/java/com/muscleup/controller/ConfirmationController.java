package com.muscleup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfirmationController {

    @GetMapping("/order-confirm")
    public String showOrderConfirmationPage() {
        return "order-confirm"; 
    }
}
