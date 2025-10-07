package com.pcmarket.pcmarket.service;

import java.util.List;

import com.pcmarket.pcmarket.entity.Seller;

public interface SellerService {

    void createSeller(Seller seller);

    Seller getSellerById(int id);

    List<Seller> getAllSellers();

    void updateSeller(int id, Seller seller);

    void deleteSeller(int id);
}