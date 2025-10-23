package com.pcmarket.pcmarket.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.dto.AddCartItemRequest;
import com.pcmarket.pcmarket.dto.CartDTO;
import com.pcmarket.pcmarket.dto.OrderDTO;
import com.pcmarket.pcmarket.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    
    private CartService cartService;
    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping("{userId}")
    public CartDTO getCartByUserId(@PathVariable int userId) {
        return cartService.getCartById(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddCartItemRequest request) {
        CartDTO updatedCart = cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable int cartItemId) {
        cartService.deletCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public OrderDTO checkout(@RequestParam("userId") int userId) {
        return cartService.checkout(userId);
    }
}
