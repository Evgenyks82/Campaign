package com.example.compaigntest;

import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.model.Campaign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {
    private static ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void checkMappingDTOToModel() {
        CampaignRequestDTO campaignDTO = new CampaignRequestDTO();
        campaignDTO.setName("Ikea sale");
        Map<Integer, Double> productsWithBids = new HashMap<>();
        productsWithBids.put(1, 99.0);
        productsWithBids.put(12, 56.0);
        productsWithBids.put(11, 9.0);
        productsWithBids.put(99, 8.0);

        campaignDTO.setProductsWithBids(productsWithBids);

        Campaign campaign = modelMapper.map(campaignDTO, Campaign.class);
        assertEquals(campaignDTO.getName(), campaign.getName());
        assertEquals(campaignDTO.getProductsWithBids().get(12), campaign.getProductsWithBids().get(12));

    }

    @Test
    public void checkMappingModelToDTO() {
        Campaign campaign = new Campaign();
        campaign.setName("Ikea sale");
        Map<Integer, Double> productsWithBids = new HashMap<>();
        productsWithBids.put(1, 99.0);
        productsWithBids.put(12, 56.0);
        productsWithBids.put(11, 9.0);
        productsWithBids.put(99, 8.0);

        campaign.setProductsWithBids(productsWithBids);

        CampaignRequestDTO campaignDTO = modelMapper.map(campaign, CampaignRequestDTO.class);
        assertEquals(campaignDTO.getName(), campaign.getName());
        assertEquals(campaignDTO.getProductsWithBids().get(12), campaign.getProductsWithBids().get(12));

    }
}
