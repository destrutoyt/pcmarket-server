package com.pcmarket.pcmarket.service;

import java.util.List;

import com.pcmarket.pcmarket.dto.ProductDTO;
import com.pcmarket.pcmarket.entity.Product;

public interface ProductService {

    List<ProductDTO> findAllProducts();
    Product findProductById(Long id);
    Product saveProduct(Product product);
    void deleteProductById(Long id);

}
