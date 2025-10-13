package com.pcmarket.pcmarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcmarket.pcmarket.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}