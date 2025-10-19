package com.pcmarket.pcmarket.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcmarket.pcmarket.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(int cartId);

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

    void deleteByCartId(int cartId);
}
