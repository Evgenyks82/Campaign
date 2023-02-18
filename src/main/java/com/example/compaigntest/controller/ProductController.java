package com.example.compaigntest.controller;


import com.example.compaigntest.model.Product;
import com.example.compaigntest.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public Map<Integer, Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/get/{category}")
    public Product getProduct(@PathVariable("category") @NotBlank String category){
        log.info("Get product with max bid fo category {}", category);
        return productService.getMostExpensiveProductFromCategory(category);

    }
}
