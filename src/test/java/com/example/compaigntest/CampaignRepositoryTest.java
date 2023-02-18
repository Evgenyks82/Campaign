package com.example.compaigntest;

import com.example.compaigntest.model.Campaign;
import com.example.compaigntest.repository.CampaignRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CampaignRepositoryTest {
    @InjectMocks
    private CampaignRepositoryImpl campaignRepository;

    @Test
    public void getActiveCampaigns() {

        Campaign campaign1 = Campaign.builder().id(UUID.randomUUID().toString()).name("First campaign").startDate(LocalDate.now().minusDays(2)).build();
        Campaign campaign2 = Campaign.builder().id(UUID.randomUUID().toString()).name("Second campaign").startDate(LocalDate.now().minusDays(12)).build();
        Campaign campaign3 = Campaign.builder().id(UUID.randomUUID().toString()).name("Third campaign").startDate(LocalDate.now().minusDays(10)).build();
        Campaign campaign4 = Campaign.builder().id(UUID.randomUUID().toString()).name("Fourth campaign").startDate(LocalDate.now()).build();
        Campaign campaign5 = Campaign.builder().id(UUID.randomUUID().toString()).name("Fifths campaign").startDate(LocalDate.now().minusDays(9)).build();


        campaignRepository.addCampaign(campaign1);
        campaignRepository.addCampaign(campaign2);
        campaignRepository.addCampaign(campaign3);
        campaignRepository.addCampaign(campaign4);
        campaignRepository.addCampaign(campaign5);

        List<Campaign> startedCampaigns = campaignRepository.getActiveCampaigns();
        Assertions.assertNotNull(startedCampaigns);
        Assertions.assertEquals(3, startedCampaigns.size());
        Assertions.assertTrue(campaignRepository.getActiveCampaigns().contains(campaign1));
        Assertions.assertFalse(campaignRepository.getActiveCampaigns().contains(campaign2));
        Assertions.assertFalse(campaignRepository.getActiveCampaigns().contains(campaign3));
        Assertions.assertTrue(campaignRepository.getActiveCampaigns().contains(campaign4));
        Assertions.assertTrue(campaignRepository.getActiveCampaigns().contains(campaign5));

    }


}
