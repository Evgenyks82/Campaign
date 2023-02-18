package com.example.compaigntest.service;

import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.dto.CampaignResponseDTO;
import com.example.compaigntest.model.Campaign;
import com.example.compaigntest.model.Product;
import com.example.compaigntest.repository.CampaignRepository;
import com.example.compaigntest.repository.ProductRepository;
import com.example.compaigntest.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CampaignServiceImpl implements CampaignService {

    private ModelMapper modelMapper;

    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CampaignServiceImpl(ModelMapper modelMapper, CampaignRepository campaignRepository, ProductRepository productRepository) {
        this.campaignRepository = campaignRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public CampaignResponseDTO createCampaign(CampaignRequestDTO campaignDTO) {
        log.info("Creating campaign {}", campaignDTO.getName());
        Campaign campaign = modelMapper.map(campaignDTO, Campaign.class);

        List<Product> productByIds = productRepository.getProductByIds(campaignDTO.getProductsWithBids().keySet());

        Map<String, List<Product>> categoriesInCampaign = groupProductByCategories(productByIds);
        campaign.setCategories(categoriesInCampaign);

        setBestBidByCategory(campaignDTO, campaign, productByIds);

        Campaign resultCampaign = campaignRepository.addCampaign(campaign);
        CampaignResponseDTO campaignResponseDTO = modelMapper.map(resultCampaign, CampaignResponseDTO.class);

        return campaignResponseDTO;
    }

    private Map<String, List<Product>> groupProductByCategories(List<Product> products) {
        Map<String, List<Product>> categoriesInCampaign = products.stream()
                .map(Product::getCategories)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        category -> products.stream()
                                .filter(product -> product.getCategories().contains(category))
                                .collect(Collectors.toList())));
        return categoriesInCampaign;
    }

    private void setBestBidByCategory(CampaignRequestDTO campaignDTO, Campaign campaign, List<Product> productByIds) {
         productByIds.forEach(product->{
            product.getCategories().forEach(
                    category-> {
                        handleBestBid(campaign,product, category);
                    }
            );
            handleBestBid(campaign, product, Constants.ALL_PRODUCTS);
        });
    }

    private void handleBestBid(Campaign campaign, Product product, String category) {
        Integer productId = product.getId();
        Double productBid = campaign.getProductsWithBids().get(productId);

        Integer bestBidProductId = campaign.getCategoriesWithBestBidProductId().get(category);

        if(bestBidProductId == null){
            campaign.getCategoriesWithBestBidProductId().put(category, productId);
        }else{
            Double currentBestBid = campaign.getProductsWithBids().get(bestBidProductId);
            if(productBid > currentBestBid){
                campaign.getCategoriesWithBestBidProductId().put(category, productId);
            }
        }
    }
}
