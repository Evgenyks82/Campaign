package com.example.compaigntest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class CampaignRequestDTO {
    @NotBlank(message = "Name missing in Campaign")
    private String name;
    @JsonIgnore
    private final LocalDate startDate = LocalDate.now();
    @NotEmpty(message = "Product/Bid missing in Campaign")
    private Map<Integer, Double> productsWithBids;
}
