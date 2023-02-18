package com.example.compaigntest.service;

import com.example.compaigntest.model.Product;

import java.util.Map;

public interface ProductService {
    Map<Integer, Product> getProducts();
    Product getProductById(Integer id);
    Product getMostExpensiveProductFromCategory(String category);
}
