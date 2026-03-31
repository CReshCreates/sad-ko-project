package com.spring.smbs_backend.controller;

import com.spring.smbs_backend.DTO.Response.SalesReport.SalesReportResponse;
import com.spring.smbs_backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> getDashboard(){
        try{
            return new ResponseEntity<>(dashboardService.getDashboardInfo(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
