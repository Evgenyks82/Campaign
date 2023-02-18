package com.example.compaigntest.repository;

import com.example.compaigntest.model.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Data
@Component
public class ProductRepositoryImpl implements ProductRepository{
    private static int PRODUCT_ID = 0;
    private static int PRODUCTS_COUNT = 100;

    private Map<Integer, Product> mappedProducts;

    {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        mappedProducts = new HashMap<>();
        List<String> categories = Arrays.asList("Car", "Toys", "Electronics", "Clothing", "Sports", "Home", "Beauty");

        for (int i = 0; i < PRODUCTS_COUNT; i++) {

            ++PRODUCT_ID;
            mappedProducts.put(PRODUCT_ID, new Product(PRODUCT_ID, "The product " + PRODUCT_ID, getPrice(formatter), "123456487897", getRandomCategory(categories, PRODUCT_ID)));
        }

    }


    private static Double getPrice(DecimalFormat formatter){
        Double price = ThreadLocalRandom.current().nextDouble(1, PRODUCTS_COUNT);
        return Double.valueOf(formatter.format(price));
    }
    private static List<String> getRandomCategory(List<String> categories, int PRODUCT_ID) {
        List<String> productCategories = new ArrayList<>();

        productCategories.add(categories.get(new Random().nextInt(categories.size())));

        if (PRODUCT_ID % 3 == 0 || PRODUCT_ID % 5 == 0) {
            String category = categories.get(new Random().nextInt(categories.size()));
            if (!productCategories.contains(category))
                productCategories.add(category);
        }

        return productCategories;
    }

    @Override
    public Product getProductById(Integer id) {
        return mappedProducts.get(id);
    }

    @Override
    public List<Product> getProductByIds(Set<Integer> ids) {
        return ids.stream().map(i-> mappedProducts.get(i)).filter(Objects::nonNull).collect(Collectors.toList());
    }


}
