package com.pcmarket.pcmarket.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.dto.ProductDTO;
import com.pcmarket.pcmarket.service.ProductService;

@CrossOrigin(origins = "http://localhost:4200/") // Allows Angular frontend to access this API
@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Get method to list all products
    @GetMapping("/productList")
    public List<ProductDTO> findAll() {
        return productService.findAllProducts();
    }
}
