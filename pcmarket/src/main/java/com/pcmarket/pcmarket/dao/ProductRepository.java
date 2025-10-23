package com.pcmarket.pcmarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcmarket.pcmarket.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}