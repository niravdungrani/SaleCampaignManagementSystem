package com.example.SaleCampaignManagementSystem.Service;

import com.example.SaleCampaignManagementSystem.Model.PriceHistory;
import com.example.SaleCampaignManagementSystem.Model.Product;
import com.example.SaleCampaignManagementSystem.Model.ProductDto;
import com.example.SaleCampaignManagementSystem.Model.Sales;
import com.example.SaleCampaignManagementSystem.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesServices salesServices;

    @Autowired
    private PriceHistoryServices priceHistoryServices;

    public Product addproduct(Product p) {
        Product product = productRepository.save(p);

        PriceHistory ph = new PriceHistory();
        ph.setActualprice(product.getCurrentPrice());
        ph.setProductid(product.getId());

        List<Double> price = new ArrayList<>();
        price.add(product.getCurrentPrice());
        ph.setPhistory(price);

        List<LocalDateTime> time = new ArrayList<>();
        time.add(LocalDateTime.now());
        ph.setChangetime(time);


        priceHistoryServices.addhistory(ph);

        return product;
    }

    public List<Product> getproduct() {
        return productRepository.findAll();
    }

    public ProductDto getpage(int pageno, int pagesize) {
        if (pageno > 0){
            pageno -= 1;
        }
        PageRequest pageRequest = PageRequest.of(pageno  ,pagesize);

        Page<Product> page =productRepository.findAll(pageRequest);



        ProductDto pd = new ProductDto();

        pd.setProductList(page.getContent());
        pd.setPage(page.getNumber());
        pd.setPagesize(page.getSize());
        pd.setTotalpage(page.getTotalPages());
        return pd;
    }

    public List<Product> getsalesdiscount() {

        List<Sales> onsales = salesServices.getpresent();
        List<Sales> aftersales = salesServices.getpast();

        List<Product> product = productRepository.findAll();
        if (onsales.size() > 0){

            product.forEach((p)->{

                PriceHistory getbypid = priceHistoryServices.getbypid(p.getId());


                double cuurentprice = getbypid.getActualprice();
                double percentage = p.getDiscount();

                double discountprice = cuurentprice - ((cuurentprice * percentage) / 100);
                p.setCurrentPrice(discountprice);

                List<Double> price = getbypid.getPhistory();
                price.add(discountprice);
                getbypid.setPhistory(price);

                List<LocalDateTime> time = getbypid.getChangetime();
                time.add(LocalDateTime.now());
                getbypid.setChangetime(time);

                productRepository.save(p);

            });
            return product;
        }


        if (aftersales.size() > 0){

            product.forEach((p)->{

                PriceHistory getbypid = priceHistoryServices.getbypid(p.getId());


                double cuurentprice = getbypid.getActualprice();
//                double percentage = p.getDiscount();

//                double discountprice = cuurentprice - ((cuurentprice * percentage) / 100);
                p.setCurrentPrice(cuurentprice);
                productRepository.save(p);

            });
            return product;
        }


        return product;
    }
}
