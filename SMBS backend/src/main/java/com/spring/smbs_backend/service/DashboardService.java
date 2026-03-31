package com.spring.smbs_backend.service;

import com.spring.smbs_backend.DTO.Response.Dashboard.*;
import com.spring.smbs_backend.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    public DashboardResponse getDashboardInfo(){
        List<ActiveCashiers> activeCashiers = dashboardRepository.getActiveCashiers();
        List<CustomerGrowthData> customerGrowthData = dashboardRepository.getCustomerGrowthData();
        DashboardStats dashboardStats = dashboardRepository.getDashboardStats();
        List<SalesOverview> salesOverview = dashboardRepository.getSalesOverview();
        List<TopSellingProductsData> topSellingProducts = dashboardRepository.getTopSellingProducts();

        return new DashboardResponse(dashboardStats, customerGrowthData,salesOverview,topSellingProducts,activeCashiers);
    }
}
