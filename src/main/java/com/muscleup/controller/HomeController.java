package com.muscleup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.muscleup.entity.Product;
import com.muscleup.service.ProductService;
import com.muscleup.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String redirectToLogin(HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("currentUser ") == null) {
            return "redirect:/users/login"; 
        }
        return "redirect:/home";
    }
    
    
    //
    @GetMapping("/home")
    public String showHome(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            HttpSession session,
            Model model
    ) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/users/login";
        }
//view products,search products,filter by category
        List<Product> products;

        if (keyword != null && !keyword.isBlank()) {
           
            products = productService.searchProducts(keyword);
        } else if (category != null && !category.isBlank() && !category.equalsIgnoreCase("ALL")) {
           
            products = productService.getProductsByCategory(category);
        } else {
           
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", List.of("Supplements", "Accessories"));
        return "home";
    }


//for add product button
    @PostMapping("/home/add-product")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam double price,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
       
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/users/login";
        }

   
        if (name.isBlank() || category.isBlank() || price <= 0) {
            redirectAttributes.addFlashAttribute("error", "Please provide valid product details.");
            return "redirect:/home";
        }

        Product newProduct = new Product(name, price, category);
        productService.addProduct(newProduct);

        redirectAttributes.addFlashAttribute("success", "Product added successfully!");
        return "redirect:/home";
    }

        



}
