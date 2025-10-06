package com.muscleup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muscleup.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by category (Supplements/Accessories)
	List<Product> findByCategoryIgnoreCase(String category);

    //Search by name 
	List<Product> findByNameContainingIgnoreCase(String keyword);



}
