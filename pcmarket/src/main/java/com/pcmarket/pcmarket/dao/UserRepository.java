package com.pcmarket.pcmarket.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcmarket.pcmarket.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
