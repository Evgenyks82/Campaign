package com.example.compaigntest.controller;


import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.dto.CampaignResponseDTO;
import com.example.compaigntest.service.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;



@RestController
@RequestMapping("/campaign")
@Slf4j
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping(value = "/new", consumes = {"application/json"})
    public CampaignResponseDTO createCampaign(@Valid @RequestBody CampaignRequestDTO campaign){
        log.info("Create campaign request");
        return campaignService.createCampaign(campaign);
    }

}
