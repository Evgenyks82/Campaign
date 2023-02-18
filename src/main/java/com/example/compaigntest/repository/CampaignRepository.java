package com.example.compaigntest.repository;

import com.example.compaigntest.model.Campaign;

import java.util.List;

public interface CampaignRepository {
    List<Campaign> getActiveCampaigns();

    Integer getProductIdWithMaxBid(String category);

    Campaign addCampaign(Campaign campaign);
}
