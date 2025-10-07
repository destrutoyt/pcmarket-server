package com.pcmarket.pcmarket.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcmarket.pcmarket.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
