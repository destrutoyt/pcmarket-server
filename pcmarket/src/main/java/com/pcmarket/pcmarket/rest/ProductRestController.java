package com.pcmarket.pcmarket.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.dto.ProductDTO;
import com.pcmarket.pcmarket.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // Get method to list all products
    @GetMapping
    public List<ProductDTO> fetchProducts() {
        return productService.findAllProducts();
    }

    // Get method to find a product by its ID
    @GetMapping("/{id}")
    public ProductDTO fetchProductById(@PathVariable int id) {
        return productService.findProductById(id);
    }
}
