package com.example.compaigntest.service;

import com.example.compaigntest.constant.Constants;
import com.example.compaigntest.model.Product;
import com.example.compaigntest.repository.CampaignRepository;
import com.example.compaigntest.repository.ProductRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CampaignRepository campaignRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CampaignRepository campaignRepository) {
        this.productRepository = productRepository;
        this.campaignRepository = campaignRepository;
    }

    public Map<Integer, Product> getProducts(){
        return productRepository.getMappedProducts();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Product getMostExpensiveProductFromCategory(String category) {
        log.info("Getting most expensive product for category {}", category);
        Integer productId = campaignRepository.getProductIdWithMaxBid(category);
        if(productId == -1){
            productId = campaignRepository.getProductIdWithMaxBid(Constants.ALL_PRODUCTS);
        }

        return getProductById(productId);
    }


}
