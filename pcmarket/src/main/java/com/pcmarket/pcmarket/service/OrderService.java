package com.pcmarket.pcmarket.service;

import java.util.List;

import com.pcmarket.pcmarket.dto.OrderDTO;

public interface OrderService {
    List<OrderDTO> getOrdersByBuyerId(int buyerId);
}
