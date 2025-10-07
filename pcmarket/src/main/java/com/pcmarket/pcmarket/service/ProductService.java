package com.pcmarket.pcmarket.service;

import java.util.List;

import com.pcmarket.pcmarket.dto.ProductDTO;
import com.pcmarket.pcmarket.entity.Product;

public interface ProductService {

    List<ProductDTO> findAllProducts();
    ProductDTO findProductById(int id);
    ProductDTO saveProduct(Product product);
    void deleteProductById(int id);

}
