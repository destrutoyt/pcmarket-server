package com.pcmarket.pcmarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcmarket.pcmarket.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
}