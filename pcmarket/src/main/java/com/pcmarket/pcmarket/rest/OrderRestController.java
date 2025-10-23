package com.pcmarket.pcmarket.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.dto.OrderDTO;
import com.pcmarket.pcmarket.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
    
    private OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable int userId) {
        return orderService.getOrdersByBuyerId(userId);
    }
}
