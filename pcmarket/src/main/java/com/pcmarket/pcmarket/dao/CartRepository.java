package com.pcmarket.pcmarket.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcmarket.pcmarket.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserId(int userId);

    boolean existsByUserId(int userId);

    void deleteByUserId(int userId);
}
