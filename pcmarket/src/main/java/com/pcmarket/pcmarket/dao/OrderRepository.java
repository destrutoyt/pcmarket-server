package com.pcmarket.pcmarket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcmarket.pcmarket.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyerId(int buyerId);
}
