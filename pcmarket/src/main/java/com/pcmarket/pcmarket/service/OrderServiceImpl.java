package com.pcmarket.pcmarket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pcmarket.pcmarket.dao.OrderRepository;
import com.pcmarket.pcmarket.dao.ProductRepository;
import com.pcmarket.pcmarket.dao.SellerRepository;
import com.pcmarket.pcmarket.dto.OrderDTO;
import com.pcmarket.pcmarket.dto.OrderItemDTO;
import com.pcmarket.pcmarket.entity.Order;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
            SellerRepository sellerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<OrderDTO> getOrdersByBuyerId(int buyerId) {
        List<Order> orders = orderRepository.findByBuyerId(buyerId);

        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setOrderId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());

            List<OrderItemDTO> items = order.getOrderItems().stream().map(item -> {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setPrice(item.getPrice());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setProductName(
                        productRepository.findById(item.getProductId())
                                .map(p -> p.getProductName())
                                .orElse("Unknown Product"));
                itemDTO.setSellerName(
                        sellerRepository.findById(item.getSellerId())
                                .map(s -> s.getShopName())
                                .orElse("Unknown Seller"));
                itemDTO.setStatus(item.getStatus()); // 👈 new line
                return itemDTO;
            }).collect(Collectors.toList());
            dto.setOrderItems(items);
            return dto;
        }).collect(Collectors.toList());
    }

}
