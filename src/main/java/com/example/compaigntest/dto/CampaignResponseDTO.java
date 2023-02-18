package com.example.compaigntest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignResponseDTO {
    private String id;
    private String name;
    private LocalDate startDate;
    private Map<Integer, Double> productsWithBids;


}
