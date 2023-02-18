package com.example.compaigntest;

import com.example.compaigntest.constant.Constants;
import com.example.compaigntest.dto.CampaignRequestDTO;
import com.example.compaigntest.model.Campaign;
import com.example.compaigntest.model.Product;
import com.example.compaigntest.repository.CampaignRepository;
import com.example.compaigntest.repository.ProductRepository;
import com.example.compaigntest.service.CampaignServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CampainServiceTest {
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    ProductRepository productRepository;
    CampaignRequestDTO campaignRequestDTO;

    @Mock
    ModelMapper modelMapper;
    @Captor
    ArgumentCaptor<Campaign> compaignCaptor;

    @InjectMocks
    CampaignServiceImpl campaignService;
    private Product colaProduct;
    private Product milkProduct;
    private Campaign campaign;

    @BeforeEach
    public void init(){
        campaignRequestDTO = new CampaignRequestDTO();
        campaignRequestDTO.setName("First Campaign");

        Map<Integer, Double> productsWithBids = new HashMap<>();
        productsWithBids.put(1, 33.3);
        productsWithBids.put(2, 44.9);

        campaignRequestDTO.setProductsWithBids(productsWithBids);

        milkProduct = new Product(1,"Milk 3%", 11.9, "13246548", Arrays.asList("Milk", "Drink"));
        colaProduct = new Product(2,"CocaCola", 1.99, "13246548", Arrays.asList( "Drink"));

        campaign = Campaign.builder()
                .id(UUID.randomUUID().toString())
                .name("First Campaign")
                .productsWithBids(productsWithBids)
                .categoriesWithBestBidProductId(new HashMap<>())
                .startDate(campaignRequestDTO.getStartDate()).build();
    }



    @Test
    public void createCampaign(){

        when(productRepository.getProductByIds(anySet())).thenReturn(List.of(milkProduct, colaProduct));
        when(modelMapper.map(eq(campaignRequestDTO),eq(Campaign.class) )).thenReturn(campaign);

        campaignService.createCampaign(campaignRequestDTO);
        verify(campaignRepository).addCampaign(compaignCaptor.capture());
        Campaign captorValue = compaignCaptor.getValue();

        Map<String, List<Product>> categoryByProduct = new HashMap<>();
        categoryByProduct.put("Milk", Arrays.asList(milkProduct));
        categoryByProduct.put("Drink", Arrays.asList(milkProduct,colaProduct));

        Assertions.assertEquals(captorValue.getCategories(), categoryByProduct);

        Map<String, Integer> categoriesWithBestBidProductId = new HashMap<>();
        categoriesWithBestBidProductId.put(Constants.ALL_PRODUCTS, 2);
        categoriesWithBestBidProductId.put("Drink", 2);
        categoriesWithBestBidProductId.put("Milk", 1);

        Assertions.assertEquals(captorValue.getCategoriesWithBestBidProductId(), categoriesWithBestBidProductId);

    }

}
