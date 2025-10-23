package com.pcmarket.pcmarket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pcmarket.pcmarket.dao.SellerRepository;
import com.pcmarket.pcmarket.entity.Seller;

@Service
public class SellerServiceImpl implements SellerService {

    public SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }
    @Override
    public void createSeller(Seller seller) {
        sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerById(int id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public void updateSeller(int id, Seller seller) {
        seller.setId(id);
        sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(int id) {
        sellerRepository.deleteById(id);
    }
    
}
