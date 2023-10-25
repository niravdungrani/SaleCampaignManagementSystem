package com.example.SaleCampaignManagementSystem.Controller;

import com.example.SaleCampaignManagementSystem.Model.Product;
import com.example.SaleCampaignManagementSystem.Model.ProductDto;
import com.example.SaleCampaignManagementSystem.Service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServices productServices;

    @PostMapping("/Addproduct")
    private ResponseEntity<Product> Addproduct(@RequestBody Product p){
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.addproduct(p));

    }

    @GetMapping("/GetProduct")
    private ResponseEntity<List<Product>> getproduct(){
        return ResponseEntity.status(HttpStatus.FOUND).body(productServices.getproduct());
    }

    @GetMapping("/getpage")
    private ProductDto getpage(@RequestParam(defaultValue = "0") int pageno , @RequestParam(defaultValue = "1") int pagesize){
        return productServices.getpage(pageno , pagesize);
    }

    @GetMapping("/salesdiscount")
    private List<Product> salesdiscount(){
        return productServices.getsalesdiscount();
    }
}
