package com.pcmarket.pcmarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcmarket.pcmarket.dao.ProductRepository;
import com.pcmarket.pcmarket.dao.SellerRepository;
import com.pcmarket.pcmarket.dto.ProductDTO;
import com.pcmarket.pcmarket.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private SellerRepository sellerRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setProductName(p.getProductName());
            dto.setDescription(p.getDescription());
            dto.setPrice(p.getPrice());
            dto.setImageUrl(p.getImageUrl());

            // fetch shop name from sellerRepository
            sellerRepository.findById(p.getSellerId())
                    .ifPresent(s -> dto.setShopName(s.getShopName()));

            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public Product findProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findProductById'");
    }

    @Override
    public Product saveProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveProduct'");
    }

    @Override
    public void deleteProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductById'");
    }

}
