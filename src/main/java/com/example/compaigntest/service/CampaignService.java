package com.example.compaigntest.service;

import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.dto.CampaignResponseDTO;

public interface CampaignService {
    CampaignResponseDTO createCampaign(CampaignRequestDTO campaign);
}
