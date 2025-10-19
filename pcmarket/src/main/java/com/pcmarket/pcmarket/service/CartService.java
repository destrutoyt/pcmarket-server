package com.pcmarket.pcmarket.service;

import com.pcmarket.pcmarket.dto.CartDTO;
import com.pcmarket.pcmarket.dto.OrderDTO;

public interface CartService {

    // Main methods
    CartDTO getCartById(int cartId);

    CartDTO addItemToCart(int cartId, int productId, int quantity);

    void deletCartItem(int cartId);

    OrderDTO checkout(int userId);

}
