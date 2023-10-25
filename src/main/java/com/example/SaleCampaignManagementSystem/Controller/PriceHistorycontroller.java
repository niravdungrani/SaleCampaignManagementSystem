package com.example.SaleCampaignManagementSystem.Controller;

import com.example.SaleCampaignManagementSystem.Model.PriceHistory;
import com.example.SaleCampaignManagementSystem.Service.PriceHistoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/History")
public class PriceHistorycontroller {
    @Autowired
    private PriceHistoryServices priceHistoryServices;

    @PostMapping("/Addpricehistory")
    private PriceHistory Addhistory(@RequestBody PriceHistory p){
        return priceHistoryServices.addhistory(p);
    }

    @GetMapping("/GetHistory")
    private List<PriceHistory> GetHistory(){
        return priceHistoryServices.gethistory();
    }
}
