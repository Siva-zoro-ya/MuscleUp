package com.muscleup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muscleup.entity.User;
import com.muscleup.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user without email uniqueness check
    public User registerUser (User user) {
        return userRepository.save(user); 
    }

    // Authenticate a user
    public User login(String email, String password) throws IllegalArgumentException {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }

    // Fetch user by ID (for My Orders)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


}
