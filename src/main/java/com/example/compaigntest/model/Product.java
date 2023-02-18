package com.example.compaigntest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Product {
    //TODO: EK id is int because we use it manually when we create camping
    //TODO: EK it is better use UUID or something like
    private Integer id;
    private String name;
    private Double price;
    private String barcode;
    private List<String> categories;


}
