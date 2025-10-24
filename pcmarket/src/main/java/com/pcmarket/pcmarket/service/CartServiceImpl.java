package com.pcmarket.pcmarket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pcmarket.pcmarket.dao.CartItemRepository;
import com.pcmarket.pcmarket.dao.CartRepository;
import com.pcmarket.pcmarket.dao.OrderItemRepository;
import com.pcmarket.pcmarket.dao.OrderRepository;
import com.pcmarket.pcmarket.dao.ProductRepository;
import com.pcmarket.pcmarket.dao.SellerRepository;
import com.pcmarket.pcmarket.dto.CartDTO;
import com.pcmarket.pcmarket.dto.CartItemDTO;
import com.pcmarket.pcmarket.dto.OrderDTO;
import com.pcmarket.pcmarket.dto.OrderItemDTO;
import com.pcmarket.pcmarket.entity.Cart;
import com.pcmarket.pcmarket.entity.CartItem;
import com.pcmarket.pcmarket.entity.Order;
import com.pcmarket.pcmarket.entity.OrderItem;
import com.pcmarket.pcmarket.entity.Product;
import com.pcmarket.pcmarket.entity.Seller;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
            ProductRepository productRepository, SellerRepository sellerRepository,
            OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public CartDTO getCartById(int userID) {
        Cart cart = cartRepository.findByUserId(userID)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userID));

        List<CartItemDTO> cartItems = cart.getCartItems().stream().map(cartItem -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setCartItemId(cartItem.getId());
            dto.setProductId(cartItem.getProduct().getId());
            dto.setProductName(cartItem.getProduct().getProductName());
            dto.setSeller(sellerRepository.findById(cartItem.getProduct().getSellerId())
                    .orElseThrow(() -> new RuntimeException(
                            "Seller not found for ID: " + cartItem.getProduct().getSellerId()))
                    .getShopName());
            dto.setQuantity(cartItem.getQuantity());
            dto.setPrice(cartItem.getProduct().getPrice());
            return dto;
        }).toList();

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setUserId(userID);
        cartDTO.setCartItems(cartItems);
        cartDTO.setTotalPrice(cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        return cartDTO;
    }

    @Override
    public CartDTO addItemToCart(int userID, int productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userID)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userID));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for product ID: " + productId));

        // check if item already exists in cart
        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        // if exists, update quantity
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return getCartById(userID); // return updated cart
    }

    @Override
    public void deletCartItem(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for ID: " + cartItemId));

        // if quantity > 1, decrease by 1 else remove item
        if (cartItem.getQuantity() > 1) {
            Cart cart = cartItem.getCart();
            cart.setUpdatedAt(LocalDateTime.now());
            cartItem.setQuantity(cartItem.getQuantity() - 1);

            cartRepository.save(cart);
            cartItemRepository.save(cartItem);
        }
        else {
            cartItemRepository.delete(cartItem);
        }
    }

    @Override
    @Transactional
    public OrderDTO checkout(int userID) {
        Cart cart = cartRepository.findByUserId(userID)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userID));
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty for user ID: " + userID);
        }

        // Create order
        Order order = new Order();
        order.setBuyerId(userID);
        order.setOrderDate(new Date());
        order = orderRepository.save(order);
        final Order savedOrder = order;

        // Create order items from cart items
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setSellerId(cartItem.getProduct().getSellerId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setStatus("Pending");

            orderItems.add(orderItem);
            total += cartItem.getQuantity() * cartItem.getProduct().getPrice(); // Calculate total price
        }
        orderItemRepository.saveAll(orderItems);

        // update total in Order
        order.setTotalAmount(total);
        orderRepository.save(order);

        // clear cart after checkout
        cart.getCartItems().clear();
        cartRepository.save(cart);

        // convert to DTO and return
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setUserId(order.getBuyerId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());

        List<OrderItemDTO> orderItemDTOs = orderItems.stream().map(oi -> {
            OrderItemDTO dto = new OrderItemDTO();

            // load product from db
            Product product = productRepository.findById(oi.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found for ID: " + oi.getProductId()));
            dto.setProductName(product.getProductName());

            // load seller name
            Seller seller = sellerRepository.findById(oi.getSellerId())
                    .orElseThrow(() -> new RuntimeException("Seller not found for ID: " + oi.getSellerId()));
            dto.setSellerName(seller.getShopName());

            dto.setPrice(oi.getPrice());
            dto.setQuantity(oi.getQuantity());
            dto.setStatus(oi.getStatus());
            return dto;
        }).toList();
        orderDTO.setOrderItems(orderItemDTOs);
        return orderDTO;
    }
}
