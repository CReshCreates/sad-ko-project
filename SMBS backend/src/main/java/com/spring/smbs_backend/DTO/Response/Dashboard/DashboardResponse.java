package com.spring.smbs_backend.DTO.Response.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    DashboardStats dashboardStats;
    List<CustomerGrowthData> customerGrowthData;
    List<SalesOverview> salesOverview;
    List<TopSellingProductsData> topSellingProductsData;
    List<ActiveCashiers> activeCashiers;
}
