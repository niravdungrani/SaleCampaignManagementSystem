package com.example.SaleCampaignManagementSystem.Repository;

import com.example.SaleCampaignManagementSystem.Model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    PriceHistory findByProductid(int pid);
}
