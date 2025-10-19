package com.pcmarket.pcmarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcmarket.pcmarket.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
}