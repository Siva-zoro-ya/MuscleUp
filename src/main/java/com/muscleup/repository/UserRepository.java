package com.muscleup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muscleup.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//fetching user by email to login
	User findByEmail(String email);
}
