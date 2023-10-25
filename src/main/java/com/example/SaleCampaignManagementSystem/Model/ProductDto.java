package com.example.SaleCampaignManagementSystem.Model;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private List<Product> productList;
    private int page;
    private int pagesize;
    private long totalpage;
}
