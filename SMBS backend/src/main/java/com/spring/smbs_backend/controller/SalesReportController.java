package com.spring.smbs_backend.controller;

import com.spring.smbs_backend.DTO.Request.SalesReportRequest;
import com.spring.smbs_backend.DTO.Response.SalesReport.SalesReportResponse;
import com.spring.smbs_backend.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    @GetMapping("admin/available-years")
    public ResponseEntity<List<Integer>> getAvailableYears() {
        List<Integer> years = salesReportService.getAvailableYears();
        return ResponseEntity.ok(years);
    }

    @PostMapping("admin/sales-report")
    public ResponseEntity<SalesReportResponse> salesReport(@RequestBody SalesReportRequest salesReportRequest) {
        int year = (salesReportRequest.getYear() != null) ? salesReportRequest.getYear() : LocalDate.now().getYear();

        SalesReportResponse response = salesReportService.getReport(year);
        return ResponseEntity.ok(response);
    }
}