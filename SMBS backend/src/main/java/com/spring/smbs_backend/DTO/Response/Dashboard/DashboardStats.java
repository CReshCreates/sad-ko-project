package com.spring.smbs_backend.DTO.Response.Dashboard;

public interface DashboardStats {
    Double getTotalSalesToday();
    Integer getTotalCustomersToday();
    Integer getProductsSoldToday();
    Long getTotalProfitToday();
}
