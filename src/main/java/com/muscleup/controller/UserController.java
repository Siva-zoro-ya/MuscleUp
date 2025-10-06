package com.muscleup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.muscleup.entity.User;
import com.muscleup.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // show register page to create an account
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    //for creating a form
    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam(required = false) String mobile,
            Model model
    ) {
        try {
            User newUser  = new User(email, password, name, mobile);
            userService.registerUser (newUser );
            return "redirect:/users/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "/users/register";
        }
    }

    // show login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    //login form and submission
    @PostMapping("/login") 
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,     
            Model model              
    ) {
        try {
            User user = userService.login(email, password);  
            session.setAttribute("currentUser", user);      
            return "redirect:/home";                       
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid email or password"); 
            return "login";                                           
        }
    }




    //Logout..redirecting back to login form
  
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login?logout";
    }
}
