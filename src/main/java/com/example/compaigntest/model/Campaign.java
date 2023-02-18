package com.example.compaigntest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Campaign {

    private String id;
    private String name;
    private LocalDate startDate;
    private Map<Integer, Double> productsWithBids;
    private Map<String, Integer> categoriesWithBestBidProductId;
    private Map<String, List<Product>> categories;

    public Campaign() {
        this.id = UUID.randomUUID().toString();
        categoriesWithBestBidProductId = new HashMap<>();
    }

}
