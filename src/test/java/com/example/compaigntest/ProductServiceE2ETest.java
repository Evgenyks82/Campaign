package com.example.compaigntest;

import com.example.compaigntest.constant.Constants;
import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.model.Product;
import com.example.compaigntest.repository.ProductRepositoryImpl;
import com.example.compaigntest.service.CampaignServiceImpl;
import com.example.compaigntest.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ProductServiceE2ETest {

    @Autowired
    private CampaignServiceImpl campaignService;

    @Autowired
    private ProductServiceImpl productService;

    @BeforeEach
    public void init(){
        CampaignRequestDTO requestDTOCampaign1 = new CampaignRequestDTO();

        requestDTOCampaign1.setName("First Campaign");
        Map<Integer, Double> productsWithBids = new HashMap<>();
        productsWithBids.put(1, 33.3);
        productsWithBids.put(2, 99.9);
        productsWithBids.put(12, 29.99);
        requestDTOCampaign1.setProductsWithBids(productsWithBids);

        campaignService.createCampaign(requestDTOCampaign1);



    }

    @Test
    public void test(){

        Product mostExpensiveProductInCampaign = productService.getProductById(2);
        String category = mostExpensiveProductInCampaign.getCategories().get(0);

        Product productWithExistingCategory = productService.getMostExpensiveProductFromCategory(category);
        Assertions.assertEquals(productWithExistingCategory,mostExpensiveProductInCampaign);

        Product p2 = productService.getMostExpensiveProductFromCategory("XAXA");
        Assertions.assertEquals(p2, mostExpensiveProductInCampaign);

        Product p3 = productService.getMostExpensiveProductFromCategory(Constants.ALL_PRODUCTS);
        Assertions.assertEquals(p3, mostExpensiveProductInCampaign);
    }

}
