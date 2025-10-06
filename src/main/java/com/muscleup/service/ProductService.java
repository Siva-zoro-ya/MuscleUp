package com.muscleup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muscleup.entity.Product;
import com.muscleup.repository.ProductRepository;

@Service 
public class ProductService {

    @Autowired 
    private ProductRepository productRepository;

    //Fetches ALL products from the database.
     public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetches products by category 
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category); 
    }

       //Searches products by name
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword); 
    }
    
    //add product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

     // Fetch a single product by its ID.
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
