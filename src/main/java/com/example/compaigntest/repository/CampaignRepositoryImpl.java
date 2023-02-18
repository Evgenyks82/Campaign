package com.example.compaigntest.repository;

import com.example.compaigntest.model.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@Slf4j
public class CampaignRepositoryImpl implements CampaignRepository {

    public static final int DAYS_CAMPAIGN_ON_AIR = 10;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Map<String, Campaign> campaigns;
    private final Map<String, List<String>> startedCampaigns;//TODO:EK it is not started it is campaign with dates

    {
        campaigns = new HashMap<>();
        startedCampaigns = new HashMap<>();
    }

    public Campaign addCampaign(Campaign campaign) {
        campaigns.put(campaign.getId(), campaign);


        startedCampaigns.compute(campaign.getStartDate().format(formatter), (key, val)
                -> {
            if (val == null) {
                val = new ArrayList<>();
            }
            val.add(campaign.getId());
            return val;
        });
        return campaign;
    }

    @Override
    public List<Campaign> getActiveCampaigns() {
        log.info("Getting active campaigns");
        List<String> activeCampaignIDs = IntStream.range(0, DAYS_CAMPAIGN_ON_AIR)
                .mapToObj(i ->
                        startedCampaigns.get(LocalDate.now()
                                .minusDays(i)
                                .format(formatter)))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();

        return activeCampaignIDs.stream().map(i -> campaigns.get(i)).collect(Collectors.toList());
    }

    @Override
    public Integer getProductIdWithMaxBid(String category) {
        log.info("Getting product with highest bid for category {}", category);
        List<Integer> ids = new ArrayList<>();
        getActiveCampaigns().forEach(campaign -> {
            if (campaign.getCategoriesWithBestBidProductId().get(category) != null) {
                ids.add(campaign.getCategoriesWithBestBidProductId().get(category));
            }
        });

        if (!ids.isEmpty()) {
            return Collections.max(ids);
        }
        return -1;
    }

}
