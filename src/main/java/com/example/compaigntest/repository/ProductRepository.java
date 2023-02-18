package com.example.compaigntest.repository;

import com.example.compaigntest.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductRepository {
    Product getProductById(Integer id);

    List<Product> getProductByIds(Set<Integer> ids);

    Map<Integer, Product> getMappedProducts();
}
