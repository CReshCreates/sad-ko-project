package com.spring.smbs_backend.service;

import com.spring.smbs_backend.DTO.Response.SalesReport.*;
import com.spring.smbs_backend.repository.SalesReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesReportService {

    @Autowired
    private SalesReportRepository salesReportRepository;

    public List<Integer> getAvailableYears(){
        return salesReportRepository.getAvailableYears();
    }

    public SalesReportResponse getReport(int year) {
        SalesSummary summary = salesReportRepository.getSummary(year);
        List<ProductSales> products = salesReportRepository.getProductSales(year);
        List<YearlyProfit> chart = salesReportRepository.getYearlyProfit();
        List<TopSellingProducts> topSellingProducts = salesReportRepository.getTopSellingProductsCurrentYear(year);

        return new SalesReportResponse(summary, products, chart, topSellingProducts);
    }
}